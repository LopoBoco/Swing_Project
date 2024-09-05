import java.io.BufferedReader;
import java.io.PrintWriter;

public class HiPacket extends Packet {
    public static final String type = "HI";
    public String login;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void writeBody(PrintWriter writer) throws Exception {
        writer.println(login);
    }

    @Override
    public void readBody(BufferedReader reader) throws Exception {
        login = reader.readLine();
    }
}