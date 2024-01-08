import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Scanner t = new Scanner(System.in);
        System.out.println("[1]Webcam\n[2]Video");
        int op = t.nextInt();
        switch (op) {
            case 1 : webcam(); break;
            case 2 : video(); break;
            default : System.out.println("Eso no jala"); break;
        }
    }

    public static void webcam() {
        VideoCapture capture = new VideoCapture(0); // Puede que necesites cambiar el índice de la cámara (0 para la primera cámara, 1 para la segunda, etc.)

        if (!capture.isOpened()) {
            System.err.println("Error al abrir la cámara.");
            return;
        }

        JFrame frame = new JFrame("Webcam");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel();
        frame.add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        Mat matFrame = new Mat();
        while (true) {
            if (capture.read(matFrame)) {

                // Cambiar el tamaño del frame (opcional)
                Size size = new Size(640, 480);
                Imgproc.resize(matFrame, matFrame, size);

                // Convertir Mat a ImageIcon para mostrarlo en el JLabel
                ImageIcon imageIcon = new ImageIcon(HighGui.toBufferedImage(matFrame));
                label.setIcon(imageIcon);

                // Esperar 30 milisegundos y verificar si el usuario presionó la tecla 'q'
                if (SwingUtilities.isEventDispatchThread()) {
                    frame.pack(); // Ajustar el tamaño del frame si es necesario
                    frame.repaint();
                }
            }
        }
    }

    public static void video() {
        // Reemplaza "ruta_del_video" por la ruta completa de tu archivo de video
        String videoPath = "C:\\Users\\FDZja\\IdeaProjects\\ProyectoFinal\\Video prueba.mp4";
        VideoCapture capture = new VideoCapture(videoPath);

        if (!capture.isOpened()) {
            System.err.println("Error al abrir el video.");
            return;
        }

        JFrame frame = new JFrame("Video");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel();
        frame.add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        Mat matFrame = new Mat();
        while (true) {
            if (capture.read(matFrame)) {
                // Puedes realizar operaciones adicionales con el frame aquí si lo deseas

                // Cambiar el tamaño del frame (opcional)
                Imgproc.resize(matFrame, matFrame, new Size(640, 480));

                // Convertir Mat a ImageIcon para mostrarlo en el JLabel
                ImageIcon imageIcon = new ImageIcon(HighGui.toBufferedImage(matFrame));
                label.setIcon(imageIcon);

                // Esperar 30 milisegundos y verificar si el usuario presionó la tecla 'q'
                if (SwingUtilities.isEventDispatchThread()) {
                    frame.pack(); // Ajustar el tamaño del frame si es necesario
                    frame.repaint();
                }
            }
        }
    }
}