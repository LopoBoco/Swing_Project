import java.io.BufferedReader;
import java.io.PrintWriter;

public class MessagePacket extends Packet {
    public static final String type = "MSG";
    public int correspondentId;
    public String text;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void writeBody(PrintWriter writer) throws Exception {
        writer.println(correspondentId);
        writer.println(text);
        writer.println();
    }

    @Override
    public void readBody(BufferedReader reader) throws Exception {
        var correspondentIdText = reader.readLine();
        correspondentId = Integer.parseInt(correspondentIdText);

        text = readText(reader);
    }

}
