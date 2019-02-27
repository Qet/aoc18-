import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class InputReader {
    public InputReader(int day){
        this("input" + day + ".txt");
    }

    public InputReader(String file){
        this.fileName = "./inputs/" + file;
        this.fileLines = new ArrayList<String>();
        Read();
    }

    private void Read(){
        try (Stream<String> lines = Files.lines(Paths.get(fileName), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> fileLines.add(line));
        }
        catch (IOException ex){
            System.out.println("IO Exception! " + ex);
        }
    }

    public ArrayList<String> getLines(){
        return fileLines;
    }

    private ArrayList<String> fileLines;

    private String fileName;
}
