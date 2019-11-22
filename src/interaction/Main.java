package interaction;

import execute.AES;
import state.Block;
import util.Byte;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public abstract class Main {
    public static final Scanner SCN = new Scanner(System.in);

    public static String getValidFile(final boolean isFile, final boolean isIn){
        String path = null;
        String type = (isFile) ? "Arquivo" : "Diretório";
        String stage = (isIn) ? "entrada" : "saída";

        while(true) {
            System.out.print("Digite o local do arquivo de " + stage + ": ");
            path = SCN.nextLine();

            if (!Files.exists(Paths.get(path))) {
                System.out.println("\n" + type + " digitado não encontrado!\n");
                continue;
            }

            if (Files.isDirectory(Paths.get(path)) && isFile){
                System.out.println("\nÉ necessário digitar o caminho de um arquivo valido!\n");
                continue;
            }

            break;
        }

        return path;
    }

    public static void createFile(Path path, String filename, byte[] content) throws Exception {
        if (filename != null) {
            path = path.resolve(filename);
        }

        File f = new File(path.toString());

        if (!f.exists()) {
            f.createNewFile();
        }

        OutputStream os = new FileOutputStream(f);
        os.write(content);
        os.flush();
        os.close();
    }

    public static void main(String[] args) {
        String pathFileIn = getValidFile(true, true);
        String pathFileOut = getValidFile(false, false);
        String filename = null;

        if(Files.isDirectory(Paths.get(pathFileOut))) {
            System.out.print("Digite um nome para o arquivo de saída: ");
            filename = SCN.nextLine();
        }

        System.out.print("Digite a chave de criptografia: ");
        String keyStr = SCN.nextLine();

        System.out.print("Deseja utilizar o debug(S ou N)? ");
        boolean debug = SCN.nextLine().toUpperCase().equals("S");

        try{
            AES aes = new AES(Byte.streamToByteArray(new FileInputStream(pathFileIn)), keyStr, debug);
            List<Block> encryptedBlocks = aes.execute();
            byte[] blocksBytes = null;

            for (Block block : encryptedBlocks) {
                blocksBytes = (blocksBytes == null) ? block.convertStateToByte() :
                        Byte.addBytesToList(block.convertStateToByte(), blocksBytes);
            }

            createFile(Paths.get(pathFileOut), filename, blocksBytes);

            System.out.println("Arquivo criptografado gerado com sucesso!");
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
