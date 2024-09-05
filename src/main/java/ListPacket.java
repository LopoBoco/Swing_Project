import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ListPacket extends Packet {
    public static final String type = "LIST";

    public static class CorrespondentItem {
        public int id;
        public String login;

        public CorrespondentItem(int id, String login) {
            this.id = id;
            this.login = login;
        }
    }

    public ArrayList<CorrespondentItem> items = new ArrayList<>();

    public void addItem(int id, String login) {
        items.add(new CorrespondentItem(id, login));
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void writeBody(PrintWriter writer) throws Exception {
        for (CorrespondentItem ci : items) {
            writer.println(ci.id);
            writer.println(ci.login);
        }
        writer.println();
    }

    @Override
    public void readBody(BufferedReader reader) throws Exception {
        for (;;) {
            String firstLine = reader.readLine();
            if (firstLine.isEmpty()) break;
            String secondLine = reader.readLine();
            addItem(Integer.parseInt(firstLine), secondLine);
        }
    }
}
