package view;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.Desktop;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

public class JFLAPExecutor {
    private static String directoryJFLAP = "JFLAP";
    private static JFileChooser fc = ScreenAutomatons.fileChooser;
    private static File jflapFile;
    
    public static void openJFLAP() {
        if (Dialogs.showConfirmDialog("Abrir JFLAP", 
        "Você deseja abrir o JFLAP?") != JOptionPane.OK_OPTION){
            return;
        }

        if (isJFLAPAlreadyOpen()){
            Dialogs.showMessage("O JFLAP já se encontra aberto!");
            return;
        }

        jflapFile = getJFLAP();
        if (jflapFile == null){
            Dialogs.showMessage("O JFLAP não foi encontrado na pasta padrão!");
            if (Dialogs.showConfirmDialog("Pesquisar JFLAP", 
            "Você deseja pesquisar onde se encontra o JFLAP?") != JOptionPane.OK_OPTION){
                return;
            }

            findJFLAPFile();
        }

        try{
            Desktop.getDesktop().open(jflapFile);
        }catch (UnsupportedOperationException e){
            Dialogs.showMessage("Não foi possível abrir o arquivo .jar!");
        }catch (IOException e){
            Dialogs.showMessage("Ocorreu algum erro com o arquivo do JFLAP!");
        }
    }

    private static File getJFLAP() {
        File dir = new File(directoryJFLAP);
        File[] files = dir.listFiles();

        if (files != null){
            for (File file : files){
                if (file.getName().equals("JFLAP.jar")){
                    return file;
                }
            }
        }
        
        return null;
    }

    private static boolean isJFLAPAlreadyOpen() {
        List<ProcessHandle> processes = ProcessHandle.allProcesses().collect(Collectors.toList());
        int qtd = 0;
        for (ProcessHandle p : processes) {
            String command = p.info().command().orElse("");
            if (command.contains("javaw.exe")){
                qtd++;
                if (qtd >= 2)
                    return true;
            }
        }

        return false;
    }

    private static void findJFLAPFile() {
        fc.resetChoosableFileFilters();
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            String fileName = file.getName();
            fc.addChoosableFileFilter(new AutomatonFilter());
            if (fileName != null){
                if (!fileName.equals("JFLAP.jar")){
                    Dialogs.showMessage("O arquivo JFLAP.jar não foi selecionado!");
                    return;
                }
                jflapFile = file;
            }
        }
    }
    
}