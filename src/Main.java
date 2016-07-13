import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Main {
    private JPanel panel;
    private JButton upload;
    private String path;

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth() / 5;
        int height = (int) screenSize.getWidth() / 5;
        JFrame frame = new JFrame();
        frame.setTitle("playlist");
        frame.setSize(new Dimension(width, height));
        frame.setContentPane(new Main().panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private String getFiles() {
        String userDir = System.getProperty("user.home");
        JFileChooser fileChooser = new JFileChooser(userDir + "/Desktop");
        fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(this.panel);

        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            return path;
        }

        return null;
    }

    private void write(ArrayList<String> list) throws IOException{
        FileWriter fw = new FileWriter(path + "/lista.txt");

        for(String listItem : list){
            fw.write(listItem + System.getProperty("line.separator"));
        }

        fw.close();
    }

    public Main() {
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> names = new ArrayList<String>();
                ArrayList<String> items = new ArrayList<String>();

                path = getFiles();

                File folder = new File(path);
                File[] listOfFiles = folder.listFiles();

                for(File file : listOfFiles) {
                    if(file.isFile() && file.getName().substring(file.getName().length() - 4).equals(".mp3")) {
                        names.add(file.getName().replace(".mp3", ""));
                    }
                }

                names.add("~~~");
                items.add("~ " + names.get(0).substring(0, 1).toUpperCase() + " ~");

                for(int i = 0; i < names.size() - 1; i++) {
                    items.add(names.get(i));

                    if(!names.get(i).substring(0, 1).toUpperCase().equals(names.get(i + 1).substring(0, 1).toUpperCase()) && i != names.size() - 2) {
                        items.add("");
                        items.add("~ " + names.get(i + 1).substring(0, 1).toUpperCase() + " ~");
                    }
                }

                int index = 0;
                int counter = 0;

                for(String item : items) {
                    if(!item.contains("~") && item.length() > 5) {
                        counter++;
                        items.set(index, counter + ". " + item);
                    }

                    index++;
                }

                try {
                    write(items);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Uspesno napravljena lista", "Poruka", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
