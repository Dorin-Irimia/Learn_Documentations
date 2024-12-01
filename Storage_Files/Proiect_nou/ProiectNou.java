import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.net.URL;

public class ProiectNou extends Frame {
        Toolkit tool;
        int ww, hh;
        // public DrawingPanel drawingPanel;
        public TextArea ta;
        public Image backg, info;
        public LeftPanel leftPanel;
        public DrawSheet drawSheet;
        // public InfoPanel infoPanel;
        // TextField tf;
        // Button start;
        Font f = new Font("TimesRoman", 1, 14);
        public Button btnRedeseneaza, btnMediane, btnBisectoare, btnInaltimi, btnMediatoare, btnCerculEuler, btnLaAlegere;
    public static void main (String args[]){new ProiectNou();}
    
    public ProiectNou() {
    tool=getToolkit();

	Dimension res=tool.getScreenSize();        
	ww = res.width;
	hh = res.height;	
	setResizable(false);	                  
        setTitle("Desenează un triunghi");      
        setIconImage(tool.getImage(GetResources("images/ico.gif")));
        setBackground(new Color(255, 255, 255));
        setLayout(null);	
        loadImage(); 
        repaint();
        setExtendedState(Frame.MAXIMIZED_BOTH);

        leftPanel = new LeftPanel(this);
            add(leftPanel);
            leftPanel.setBounds(25, 50, 150,  1000);

        drawSheet = new DrawSheet(this);
            add(drawSheet);
            drawSheet.setBounds(175, 50, 1000,  1000);
            drawSheet.Desenare(); // Inițializăm evenimentele de mouse

        // // sidePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Înlocuim BoxLayout cu FlowLayout
        // sidePanel.setPreferredSize(new Dimension(150, getHeight()));
        // sidePanel.setBackground(new Color(200, 200, 200));

        btnRedeseneaza = new Button("Redesenează");  
        btnMediane = new Button("Mediane");
        btnBisectoare = new Button("Bisectoare");
        btnInaltimi = new Button("Înălțimi");
        btnMediatoare = new Button("Mediatoare");
        btnCerculEuler = new Button("Cercul Euler");
        btnLaAlegere = new Button("Personalizată");

        btnRedeseneaza.setBounds(10, 10, 150, 30);
        btnMediane.setBounds(10, 30, 150, 30);
        btnBisectoare.setBounds(10, 50, 150, 30);
        btnInaltimi.setBounds(10, 70, 150, 30);
        btnMediatoare.setBounds(10, 90, 150, 30);
        btnCerculEuler.setBounds(10, 110, 150, 30);
        btnLaAlegere.setBounds(10, 130, 150, 30);

        leftPanel.add(btnRedeseneaza);
        leftPanel.add(btnMediane);
        leftPanel.add(btnBisectoare);
        leftPanel.add(btnInaltimi);
        leftPanel.add(btnMediatoare);
        leftPanel.add(btnCerculEuler);
        leftPanel.add(btnLaAlegere);

        ta = new TextArea();
        ta.setEditable(false);  // Caseta de text nu este editabilă
        ta.setBackground(new Color(100, 100, 100));  // Setează culoarea de fundal
        leftPanel.add(ta);  // Adaugă caseta de text în panoul lateral
        ta.setText(" Aici\n se vor afisa\n valorile laturilor.\n\nMade by:\n   Irimia Dorin"); // Dacă acest text apare, problema este în metoda `updateTextArea`

        // Configurarea acțiunilor butoanelor
        toggleButtons(false);
        configureButtons();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // Scalăm componentele manual
                int width = leftPanel.getWidth();
                int height = leftPanel.getHeight();
                btnRedeseneaza.setBounds(5, 10, width-10, 30);
                btnMediane.setBounds(5, 50, width-10, 30);
                btnBisectoare.setBounds(5, 90, width-10, 30);
                btnInaltimi.setBounds(5, 130, width-10, 30);
                btnMediatoare.setBounds(5, 170, width-10, 30);
                btnCerculEuler.setBounds(5, 210, width-10, 30);
                btnLaAlegere.setBounds(5, 250, width-10, 30);
                ta.setBounds(5, 390, width-10, 200);
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0); // Închidem aplicația
            }
        });

        setVisible(true);
    }


    public java.net.URL GetResources(String s) {return this.getClass().getResource(s);}

    public void loadImage(){
	try {
            MediaTracker mediatracker = new MediaTracker(this);                                 
            backg = tool.getImage(GetResources("images/backg.jpg"));
            mediatracker.addImage(backg, 0);                                               
            mediatracker.waitForAll();
        }
        catch(Throwable throwable) { }
    }

    public void configureButtons() {
        btnRedeseneaza.addActionListener(e -> {
            drawSheet.reset();
            ta.setText("");
            setTitle("Desenează un triunghi");
            toggleButtons(false);
        });

        btnMediane.addActionListener(e -> drawSheet.showMediane());
        btnBisectoare.addActionListener(e -> drawSheet.showBisectoare());
        btnInaltimi.addActionListener(e -> drawSheet.showInaltimi());
        btnMediatoare.addActionListener(e -> drawSheet.showMediatoare());
        btnCerculEuler.addActionListener(e -> drawSheet.showCerculEuler());
        btnLaAlegere.addActionListener(e -> drawSheet.showLaAlegere());
    }

    public void toggleButtons(boolean state) {
        btnMediane.setEnabled(state);
        btnBisectoare.setEnabled(state);
        btnInaltimi.setEnabled(state);
        btnMediatoare.setEnabled(state);
        btnCerculEuler.setEnabled(state);
        btnLaAlegere.setEnabled(state);
    }
}

class LeftPanel extends Panel {
    public Image backg; // Adăugăm imaginea ca membru al clasei 
    Font f;
    public TextArea ta;

    public LeftPanel(ProiectNou proiectNou) {
        this.backg = proiectNou.backg;
    	this.f = proiectNou.f;
        this.ta = proiectNou.ta;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        if (backg != null) { 
            g.drawImage(backg, 0, 0, getWidth(), getHeight(), this); // Desenăm imaginea de fundal
        }
    }
}


class DrawSheet extends Panel {
    private ArrayList<Point> points = new ArrayList<>();  
    private Point draggedPoint = null;
    private ProiectNou proiectNou; // Referință la panoul principal

        public DrawSheet(ProiectNou proiectNou) {
            this.proiectNou = proiectNou; // Salvează referința către panoul principal
            setBackground(Color.WHITE);
            // this.ta = proiectNou.ta;
            // this.proiectNou = proiectNou;
        }
    public void Desenare() {
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Point p : points) {
                    if (p.distance(e.getPoint()) < 10) {
                        draggedPoint = p;
                        return;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggedPoint = null;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (points.size() < 3) {
                    points.add(e.getPoint());
                    repaint();
                    if (points.size() == 3) {
                        proiectNou.toggleButtons(true);
                        proiectNou.setTitle("Triunghi");
                        updateTextArea();
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedPoint != null) {
                    draggedPoint.setLocation(e.getPoint());
                    repaint();
                    updateTextArea(); 
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.RED);
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            drawPoint(g2, p, Character.toString((char) ('A' + i)));
        }

        if (points.size() >= 2) {
            g2.setColor(Color.BLACK);
            g2.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
        }
        if (points.size() == 3) {
            g2.drawLine(points.get(1).x, points.get(1).y, points.get(2).x, points.get(2).y);
            g2.drawLine(points.get(2).x, points.get(2).y, points.get(0).x, points.get(0).y);
        }
    }

    public void reset() {
        points.clear();
        repaint();
    }

    public void updateTextArea() {
        if (points.size() == 3) {
            double a = points.get(0).distance(points.get(1));
            double b = points.get(1).distance(points.get(2));
            double c = points.get(2).distance(points.get(0));
            double s = (a + b + c) / 2;
            double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));

            proiectNou.ta.setText(String.format(
                "Laturi:\n" +
                "AB = %.2f\n" +
                "BC = %.2f\n" +
                "CA = %.2f\n" +
                "Aria = %.1f",
                a, b, c, area
            ));
        }
    }

    public void showMediane() {
        // Implementare Mediane
    }

    public void showBisectoare() {
        // Implementare Bisectoare
    }

    public void showInaltimi() {
        // Implementare Înălțimi
    }

    public void showMediatoare() {
        // Implementare Mediatoare
    }

    public void showCerculEuler() {
        // Implementare Cercul Euler
    }

    public void showLaAlegere() {
        // Implementare Construcție personalizată
    }

    private void drawPoint(Graphics2D g2, Point p, String label) {
        g2.fill(new Ellipse2D.Double(p.x - 5, p.y - 5, 10, 10));
        g2.drawString(label, p.x + 10, p.y - 10);
    }
}


