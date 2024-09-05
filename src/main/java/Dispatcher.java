import java.util.concurrent.LinkedBlockingQueue;

public class Dispatcher implements Runnable {
    private static final LinkedBlockingQueue<Event> packetQueue = new LinkedBlockingQueue<>();

    public static void event(Event e) {
        packetQueue.add(e);
    }

    @Override
    public void run() {
        for (;;) {
            try {
                Event e = packetQueue.take();
                processPacket(e.session, e.packet);
            } catch (InterruptedException x) {
                break;
            }
        }
    }

    private void processPacket(Session session, Packet p) {
        System.out.println("Processing packet: " + p.getType());
        try {
            switch (p.getType()) {
                case EchoPacket.type -> session.send(p);
                case HiPacket.type -> {
                    HiPacket hiP = (HiPacket) p;
                    var correspondent = Correspondent.findCorrespondent(hiP.login);
                    if (correspondent == null) {
                        session.close();
                        return;
                    }
                    session.correspondent = correspondent;
                    correspondent.activeSession = session;
                    System.out.println("Correspondent authorized, id: " + correspondent.id);
                }
                case MessagePacket.type -> {
                    MessagePacket mP = (MessagePacket) p;
                    if (session.correspondent == null) {
                        System.out.println("Non-authorized");
                        return;
                    }
                    mP.correspondentId = session.correspondent.id;
                    var correspondent = Correspondent.findCorrespondent(mP.correspondentId);
                    if (correspondent.activeSession != null) {
                        System.out.println("Sending message to correspondent, id: " + correspondent.id);
                        correspondent.activeSession.send(mP);
                    } else {
                        System.out.println("Target correspondent not connected, id: " + correspondent.id);
                    }
                }
                case ListPacket.type -> {
                    ListPacket emptyListP = new ListPacket();
                    for (var c : Correspondent.listAll()) {
                        emptyListP.addItem(c.id, c.login);
                    }
                    session.send(emptyListP);
                }
                default -> System.out.println("Unexpected packet type: " + p.getType());
            }
        } catch (Exception ex) {
            System.out.println("Dispatcher problem: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
