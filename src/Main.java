import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    private static void openZip(String saveFilePath, String saveDirPath) {

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(saveFilePath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(saveDirPath + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception er) {
            System.out.println(er.getMessage());
        }
    }

    private static GameProgress openProgress(String saveFilePath) {
        GameProgress gameProgress = null;
        try (FileInputStream fin = new FileInputStream(saveFilePath); ObjectInputStream oin = new ObjectInputStream(fin)) {
            gameProgress = (GameProgress) oin.readObject();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return gameProgress;
    }

    public static void main(String[] args) {
        String parentsPath = "C:/Users/weyma/Documents/Games/savegames/";
        openZip(parentsPath + "zip.zip", parentsPath);
        System.out.println(openProgress(parentsPath + "save1.dat").toString());
    }
}
