/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import kg2019examples_task4threedimensions.draw.IDrawer;
import kg2019examples_task4threedimensions.draw.SimpleEdgeDrawer;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.screen.ScreenConverter;
import kg2019examples_task4threedimensions.third.Camera;
import kg2019examples_task4threedimensions.third.Scene;
import models.Parallelepiped;

/**
 *
 * @author Alexey
 */
public class DrawPanel extends JPanel
        implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter sc;
    private Camera cam;
    private CameraController camController;
    
    public DrawPanel() {
        super();
        sc = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        cam = new Camera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();
        float l = 0;
        float k = 0;
        float m = 0;
        int n = 8;
        float width = 0.8f;
        float x = -width*n/2;
        float y = -width*n/2;
        float z = -width*n/2;

for (int g=0; g<n; g++) {
    l = 0;
    for (int i = 0; i < n; i++) {
        k = 0;
        for (int j = 0; j < n; j++) {
            scene.getModelsList().add(new Parallelepiped(
                    new Vector3(x + k, y + l, z + m),
                    new Vector3(x + width + k, y + width + l, z + width + m)
            ));
            k += 0.8;
        }
        l += 0.8;
    }
    m+=0.8;
}
        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);
    }
    
    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D)bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}
