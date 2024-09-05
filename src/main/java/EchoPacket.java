import java.io.BufferedReader;
import java.io.PrintWriter;

public class EchoPacket extends Packet {
    public static final String type = "ECHO";
    public String text;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void writeBody(PrintWriter writer) throws Exception {
        writer.println(text);
        writer.println();
    }

    @Override
    public void readBody(BufferedReader reader) throws Exception {
        text = readText(reader);
    }
}
