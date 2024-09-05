import java.io.BufferedReader;
import java.io.PrintWriter;

public class ByePacket extends Packet {
    public static final String type = "BYE";

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void writeBody(PrintWriter writer) throws Exception {
    }

    @Override
    public void readBody(BufferedReader reader) throws Exception {
    }
}
