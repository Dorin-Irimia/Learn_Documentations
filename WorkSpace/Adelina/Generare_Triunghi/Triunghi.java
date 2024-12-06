import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*; 
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

public class Triunghi extends Frame {
        Toolkit tool;
        int ww, hh;
        public TextArea ta;
        public Image backg, info;
        public LeftPanel leftPanel;
        public DrawSheet drawSheet;
        public boolean showMedianeFlag = false;
        public boolean showBisectoareFlag = false;
        public boolean showInaltimiFlag = false;
        public boolean showMediatoareFlag = false;
        public boolean showCerculEulerFlag = false;
        public boolean showLaAlegereFlag = false;
        String tipTriunghi = "0";
        Font f = new Font("TimesRoman", 1, 14);
        public Button btnRedeseneaza, btnMediane, btnBisectoare, btnInaltimi, btnMediatoare, btnCerculEuler, btnLaAlegere;
    public static void main (String args[]){new Triunghi();}

    public Triunghi() {
        tool=getToolkit();

        Dimension res=tool.getScreenSize();        
        ww = res.width;
        hh = res.height;	
        setResizable(true);	                  
        setTitle("Desenează un triunghi");      
        setIconImage(tool.getImage(GetResources("images/ico.gif")));
        setBackground(Color.BLACK);
        setLayout(null);	
        loadImage(); 
        repaint();
        setExtendedState(Frame.NORMAL);
        setMinimumSize(new Dimension((ww/2), (hh/2)));
        leftPanel = new LeftPanel(this);
            add(leftPanel);
            leftPanel.setBounds(25, 50, 150,  (hh-100));

        drawSheet = new DrawSheet(this);
            add(drawSheet);
            drawSheet.setBounds(175, 50, (ww-175),  (hh-100));
            drawSheet.Desenare(); // initializare evenimente mouse

        btnRedeseneaza = new Button("Redesenează");  
        btnMediane = new Button("Mediane");
        btnBisectoare = new Button("Bisectoare");
        btnInaltimi = new Button("Înălțimi");
        btnMediatoare = new Button("Mediatoare");
        btnCerculEuler = new Button("Cercul Euler");
        btnLaAlegere = new Button("Diviziuni");

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

        //ta = new TextArea();
        ta = new TextArea("", 0, 0, TextArea.SCROLLBARS_NONE);
        ta.setPreferredSize(new Dimension(200, 150));

        ta.setEditable(false);
        ta.setBackground(new Color(150,150,150));
        leftPanel.add(ta); 
        ta.setText(" Aici\n se vor afisa\n valorile laturilor.\n\nMade by:\n   Paval Adelina ");

        toggleButtons(false);   //configurarea butoanelor (inactivitatea)
        configureButtons();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = leftPanel.getWidth();   //scalez componentele
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
                System.exit(0); // inchidere aplicatie
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
            showMedianeFlag = false; // reseteaza vizibilitatea medianelor
            showBisectoareFlag = false;
            showInaltimiFlag = false;
            showMediatoareFlag = false;
            showCerculEulerFlag = false;
            showLaAlegereFlag = false;
            drawSheet.repaint();
        });

        btnMediane.addActionListener(e -> { 
            setTitle("Triunghi şi Mediană");
            showMedianeFlag = true;
            showBisectoareFlag = false;
            showInaltimiFlag = false;
            showMediatoareFlag = false;
            showCerculEulerFlag = false;
            showLaAlegereFlag = false;
            drawSheet.repaint();
        });

        btnBisectoare.addActionListener(e -> {
            setTitle("Triunghi şi Bisectoare");
            showMedianeFlag = false; 
            showBisectoareFlag = true;
            showInaltimiFlag = false;
            showMediatoareFlag = false;
            showCerculEulerFlag = false;
            showLaAlegereFlag = false;
            drawSheet.repaint();
        });

        btnInaltimi.addActionListener(e -> {
            setTitle("Triunghi şi Înălţimi");
            showMedianeFlag = false; 
            showBisectoareFlag = false;
            showInaltimiFlag = true;
            showMediatoareFlag = false;
            showCerculEulerFlag = false;
            showLaAlegereFlag = false;
            drawSheet.repaint();
        });
        btnMediatoare.addActionListener(e -> {
            setTitle("Triunghi şi Mediatoare");
            showMedianeFlag = false; 
            showBisectoareFlag = false;
            showInaltimiFlag = false;
            showMediatoareFlag = true;
            showCerculEulerFlag = false;
            showLaAlegereFlag = false;
            drawSheet.repaint();
        });
        btnCerculEuler.addActionListener(e -> {
            setTitle("Triunghi şi Cercul Euler");
            showMedianeFlag = false; 
            showBisectoareFlag = false;
            showInaltimiFlag = false;
            showMediatoareFlag = false;
            showCerculEulerFlag = true;
            showLaAlegereFlag = false;
            drawSheet.repaint();
        });
        btnLaAlegere.addActionListener(e -> {
            setTitle("Triunghi şi diviziuni fractale");
            showMedianeFlag = false; // sterge medianele cand se apasa alt buton
            showBisectoareFlag = false;
            showInaltimiFlag = false;
            showMediatoareFlag = false;
            showCerculEulerFlag = false;
            showLaAlegereFlag = true;
            drawSheet.repaint();
        });
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
    public Image backg; // se adauga imaginea ca membru al clasei 
    Font f;
    public TextArea ta;
    public LeftPanel(Triunghi triunghi) {
        this.backg = triunghi.backg;
    	this.f = triunghi.f;
        this.ta = triunghi.ta;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (backg != null) {
            int imgWidth = backg.getWidth(this);
            int imgHeight = backg.getHeight(this);
    
            if (imgWidth > 0 && imgHeight > 0) {
                for (int x = 0; x < getWidth(); x += imgWidth) {
                    for (int y = 0; y < getHeight(); y += imgHeight) {
                        g.drawImage(backg, x, y, this);
                    }
                }
            }
        }
    }
}


class DrawSheet extends Panel {
    public ArrayList<Point> points = new ArrayList<>();  
    private Point draggedPoint = null;
    private Triunghi triunghi; // referinta la panoul principal
    public boolean showMedianeFlag = false; // referintaFlag pentru a controla vizibilitatea Medianelor
    public boolean showBisectoareFlag = false; // referintaFlag pentru a controla vizibilitatea Bisectoarelor
    public boolean showInaltimiFlag = false; // referintaFlag pentru a controla vizibilitatea Inaltimilor
    public boolean showMediatoareFlag = false;  // referintaFlag pentru a controla vizibilitatea Mediatoarelor
    public boolean showCerculEulerFlag = false; // referintaFlag pentru a controla vizibilitatea CerculuiEuler
    public boolean showLaAlegereFlag = false;
    
    public double radius;
    public double point1;
    public double point2;
    public double point3;
    public double surface1;

    public DrawSheet(Triunghi triunghi) {
        this.triunghi = triunghi; // Salvează referința către panoul principal
        setBackground(Color.WHITE);
        // this.ta = triunghi.ta;
        // this.triunghi = triunghi;
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
                        triunghi.toggleButtons(true);
                        triunghi.setTitle("Triunghi");
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
            drawTriunghi(g2);
            if (triunghi.showMedianeFlag)     {showMediane(g2);        updateTextArea();}
            if (triunghi.showBisectoareFlag)  {showBisectoare(g2);     updateTextArea();}
            if (triunghi.showInaltimiFlag)    {showInaltimi(g2);       updateTextArea();}
            if (triunghi.showMediatoareFlag)  {showMediatoare(g2);     updateTextArea();}
            if (triunghi.showCerculEulerFlag) {showCerculEuler(g2);    updateTextArea();}
            if (triunghi.showLaAlegereFlag) {showLaAlegere(g2);        updateTextArea();}        
        }
    }
    private void drawTriunghi(Graphics2D g2){
        if (points.size() >= 2) {
            g2.setColor(Color.BLACK);
            g2.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
        }
        if (points.size() == 3) {
            g2.drawLine(points.get(1).x, points.get(1).y, points.get(2).x, points.get(2).y);
            g2.drawLine(points.get(2).x, points.get(2).y, points.get(0).x, points.get(0).y);  
        }
    }

    public void updateTextArea() {
        if (points.size() == 3) {
            double a = points.get(0).distance(points.get(1));
            double b = points.get(1).distance(points.get(2));
            double c = points.get(2).distance(points.get(0));
            double s = (a + b + c) / 2;
            double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
            
            if(triunghi.showBisectoareFlag){
                triunghi.ta.setText(String.format(
                    "Laturi:\n AB = %.2f\n BC = %.2f\n CA = %.2f\n Aria = %.2f\n r = %.2f\n AI = %.2f\n BI = %.2f\n CI = %.2f",a, b, c, area, radius, point1, point2, point3));
            }
            else if(triunghi.showInaltimiFlag || triunghi.showMedianeFlag){
                triunghi.ta.setText(String.format(
                    "Laturi:\n AB = %.2f\n BC = %.2f\n CA = %.2f\n Aria = %.2f\n AA' = %.2f\n BB' = %.2f\n CC' = %.2f",a, b, c, area, point1, point2, point3));
            }
            else if(triunghi.showMediatoareFlag){
                triunghi.ta.setText(String.format(
                    "Laturi:\n AB = %.2f\n BC = %.2f\n CA = %.2f\n Aria = %.2f\n Raza = %.2f\n ",a, b, c, area, radius));
            }
            else if(triunghi.showCerculEulerFlag){
                triunghi.ta.setText(String.format(
                    "Laturi:\n AB = %.2f\n BC = %.2f\n CA = %.2f\n Aria = %.2f\n rE = %.2f\n OH = %.2f\n",a, b, c, area, radius, point1));
            }
            else if(triunghi.showLaAlegereFlag){
                
                triunghi.ta.setText(String.format(
                    "Laturi:\n AB = %.2f\n BC = %.2f\n CA = %.2f\n Aria = %.2f\n A_mica = %.2f\n",a, b, c, area, point1));
            }
            else{
                triunghi.ta.setText(String.format(
                    "Laturi:\n AB = %.2f\n BC = %.2f\n CA = %.2f\n Aria = %.2f\n",a, b, c, area)); 
            }
        }
    }

    public void reset(){
        points.clear();
        repaint();
    }

    public void showMediane(Graphics2D g2) {
        Point A = points.get(0);
        Point B = points.get(1);
        Point C = points.get(2);

        Point M_AB = new Point((A.x + B.x) / 2, (A.y + B.y) / 2);   //Mijlocul laturii AB
        Point M_BC = new Point((B.x + C.x) / 2, (B.y + C.y) / 2);
        Point M_CA = new Point((C.x + A.x) / 2, (C.y + A.y) / 2);

        g2.setColor(Color.YELLOW);
        drawPoint(g2, M_AB, "C'");  //Punctul ce delimiteaza mijlocul laturii
        drawPoint(g2, M_BC, "A'");
        drawPoint(g2, M_CA, "B'");

        g2.setColor(Color.BLUE);
        g2.drawLine(A.x, A.y, M_BC.x, M_BC.y); // Mediana din A catre mijlocul BC
        g2.drawLine(B.x, B.y, M_CA.x, M_CA.y); 
        g2.drawLine(C.x, C.y, M_AB.x, M_AB.y); 

        point1 = A.distance(M_BC);  // Distanta din A pana la mijlocul BC
        point2 = B.distance(M_CA); 
        point3 = C.distance(M_AB); 

        double Gx = (A.x + B.x + C.x) / 3.0;    //punctul de intersectie al medianelor
        double Gy = (A.y + B.y + C.y) / 3.0;
        Point G = new Point((int) Gx, (int) Gy);

        g2.setColor(Color.YELLOW);
        drawPoint(g2, G, "G");
    }


    public void showBisectoare(Graphics2D g2) {
        
        Point A = points.get(0);   
        Point B = points.get(1);
        Point C = points.get(2);
        double a = B.distance(C); // Latura BC
        double b = C.distance(A); 
        double c = A.distance(B); // Latura AB
        double s = (a + b + c) / 2; //perimetrul
        double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));  //aria
        Point incentru = calculateIncenter(A, B, C, a, b, c);   //centrul cercului inscris
        
        radius = area / s;  //raza cerc inscris
        g2.setColor(Color.GRAY);
        g2.fill(new Ellipse2D.Double(incentru.x - radius, incentru.y - radius, 2 * radius, 2 * radius));
        drawTriunghi(g2);

        g2.setColor(Color.BLUE);
        g2.drawLine(A.x, A.y, incentru.x, incentru.y); // Bisectoarea din A
        g2.drawLine(B.x, B.y, incentru.x, incentru.y); 
        g2.drawLine(C.x, C.y, incentru.x, incentru.y); 

        g2.setColor(Color.YELLOW);
        drawPoint(g2, incentru, "I"); // Punctul de intersectie al bisectoarelor

        point1 = distanceFromPointToLine(incentru, B, C);
        point2 = distanceFromPointToLine(incentru, C, A);
        point3 = distanceFromPointToLine(incentru, A, B);
    }
        private Point calculateIncenter(Point A, Point B, Point C, double a, double b, double c) {   //centrul cercului inscris
            double ax = A.x, ay = A.y;
            double bx = B.x, by = B.y;
            double cx = C.x, cy = C.y;
            double ix = (a * ax + b * bx + c * cx) / (a + b + c);
            double iy = (a * ay + b * by + c * cy) / (a + b + c);
            return new Point((int) ix, (int) iy);
        }
        private double distanceFromPointToLine(Point p, Point l1, Point l2) {   // Calcularea distantei de la un punct la o dreapta
            double numerator = Math.abs((l2.y - l1.y) * p.x - (l2.x - l1.x) * p.y + l2.x * l1.y - l2.y * l1.x);
            double denominator = Math.sqrt(Math.pow(l2.y - l1.y, 2) + Math.pow(l2.x - l1.x, 2));
            return numerator / denominator;
        }


    public void showInaltimi(Graphics2D g2) {
            
        Point A = points.get(0);
        Point B = points.get(1);
        Point C = points.get(2);
        Line2D.Double laturaBC = new Line2D.Double(B, C);
        Line2D.Double laturaCA = new Line2D.Double(C, A);
        Line2D.Double laturaAB = new Line2D.Double(A, B);
        Point H_A = calculatePerpendicular(A, laturaBC);
        Point H_B = calculatePerpendicular(B, laturaCA);
        Point H_C = calculatePerpendicular(C, laturaAB);

        Point ortocentru = calculateIntersection(H_A, A, H_B, B);
        g2.drawLine(A.x, A.y, H_A.x, H_A.y);    
        g2.drawLine(B.x, B.y, H_B.x, H_B.y);
        g2.drawLine(C.x, C.y, H_C.x, H_C.y);

        g2.setColor(Color.BLUE);
        g2.drawLine(A.x, A.y, ortocentru.x, ortocentru.y); // Înălțimea din A
        g2.drawLine(B.x, B.y, ortocentru.x, ortocentru.y); 
        g2.drawLine(C.x, C.y, ortocentru.x, ortocentru.y); 

        drawPoint(g2, H_A, "A'");
        drawPoint(g2, H_B, "B'");
        drawPoint(g2, H_C, "C'");

        g2.setColor(Color.YELLOW);
        drawPoint(g2, ortocentru, "H");

        point1 = A.distance(H_A);
        point2 = B.distance(H_B);
        point3 = C.distance(H_C);
    }
        private Point calculatePerpendicular(Point P, Line2D.Double line) { // Functia pentru calculul punctului de intersectie dintre o perpendiculara si o dreapta
            double x1 = line.getX1(), y1 = line.getY1();
            double x2 = line.getX2(), y2 = line.getY2();
            double slope = (y2 - y1) / (x2 - x1);   //panta dreptei
            double perpendicularSlope = -1 / slope;
            double b = P.y - perpendicularSlope * P.x;  //ecuatia perpendicularei
            double intersectX = (b - (y1 - slope * x1)) / (slope - perpendicularSlope); //punct de intersectie
            double intersectY = slope * intersectX + (y1 - slope * x1);
            return new Point((int) intersectX, (int) intersectY);
        }
        private Point calculateIntersection(Point P1, Point P2, Point Q1, Point Q2) {   // Functia pentru calculul punctului de intersectie al doua drepte
            double a1 = P2.y - P1.y;
            double b1 = P1.x - P2.x;
            double c1 = a1 * P1.x + b1 * P1.y;
            double a2 = Q2.y - Q1.y;
            double b2 = Q1.x - Q2.x;
            double c2 = a2 * Q1.x + b2 * Q1.y;
            double determinant = a1 * b2 - a2 * b1;
            if (determinant == 0) {
                return null;    //daca dreptele sunt paralele
            }
            double x = (b2 * c1 - b1 * c2) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;
            return new Point((int) x, (int) y);
        }
    

    public void showMediatoare(Graphics2D g2) {
        if (points.size() != 3) {
            return;
        }
        Point A = points.get(0);
        Point B = points.get(1);
        Point C = points.get(2);
        Point circumcenter = calculateCircumcentru(A, B, C);
        radius = circumcenter.distance(A); // Distanta de la circumcentru la unul din varfuri

        g2.setColor(Color.YELLOW);
        drawPoint(g2, circumcenter, "O");   //circumcentru
        g2.setColor(Color.BLUE);
        g2.drawOval((int) (circumcenter.x - radius), (int) (circumcenter.y - radius),   //cercul circumscris
                    (int) (2 * radius), (int) (2 * radius));

        Point M_AB = new Point((A.x + B.x) / 2, (A.y + B.y) / 2);   //mijloacele laturilor
        Point M_BC = new Point((B.x + C.x) / 2, (B.y + C.y) / 2);
        Point M_CA = new Point((C.x + A.x) / 2, (C.y + A.y) / 2);
        g2.setColor(Color.YELLOW);
        drawPoint(g2, M_AB, "C'"); // Mijlocul AB
        drawPoint(g2, M_BC, "A'"); 
        drawPoint(g2, M_CA, "B'"); 

        g2.setColor(Color.BLUE);
        g2.drawLine(M_AB.x, M_AB.y, circumcenter.x, circumcenter.y); // Mediatoarea AB
        g2.drawLine(M_BC.x, M_BC.y, circumcenter.x, circumcenter.y); 
        g2.drawLine(M_CA.x, M_CA.y, circumcenter.x, circumcenter.y); 
    }

    public void showCerculEuler(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Point A = points.get(0);
        Point B = points.get(1);
        Point C = points.get(2);
        Point M_AB = new Point((A.x + B.x) / 2, (A.y + B.y) / 2);   //mijloacele laturilor
        Point M_BC = new Point((B.x + C.x) / 2, (B.y + C.y) / 2);
        Point M_CA = new Point((C.x + A.x) / 2, (C.y + A.y) / 2);

        Point O = calculateCircumcentru(A, B, C);
        Point H = calculateOrtocentru(A, B, C);
        point1 = O.distance(H);
        Point E = new Point((O.x + H.x) / 2, (O.y + H.y) / 2);  //mijlocul segmentului OH
        Point H_A = calculateHeight(A, B, C);
        Point H_B = calculateHeight(B, C, A);
        Point H_C = calculateHeight(C, A, B);
        radius = calculateDistance(E, M_AB);

        g2.setColor(Color.GREEN);
        g2.drawOval((int) (E.x - radius), (int) (E.y - radius), (int) (2 * radius), (int) (2 * radius));    //desenare cerc Euler
        g2.setColor(new Color(211, 211, 211));
        g2.fillOval((int) (E.x - radius), (int) (E.y - radius), (int) (2 * radius), (int) (2 * radius));

        drawTriunghi(g2);
        g2.setColor(Color.BLUE);
        g2.drawLine(M_AB.x, M_AB.y, O.x, O.y);  //ortocentru
        g2.drawLine(M_BC.x, M_BC.y, O.x, O.y);
        g2.drawLine(M_CA.x, M_CA.y, O.x, O.y);
        g2.drawLine(A.x, A.y, H_A.x, H_A.y);    //inaltimi
        g2.drawLine(B.x, B.y, H_B.x, H_B.y);
        g2.drawLine(C.x, C.y, H_C.x, H_C.y);
        g2.setColor(Color.RED);
        g2.drawLine(O.x, O.y, H.x, H.y);    //segmentul OH
        g2.setColor(Color.YELLOW);
        drawPoint(g2, M_AB, "");    //cele 9 puncte de pe cerc
        drawPoint(g2, M_BC, "");
        drawPoint(g2, M_CA, "");
        drawPoint(g2, H_A, "");
        drawPoint(g2, H_B, "");
        drawPoint(g2, H_C, "");

        Point N_A = new Point((A.x + H.x) / 2, (A.y + H.y) / 2);    
        Point N_B = new Point((B.x + H.x) / 2, (B.y + H.y) / 2);
        Point N_C = new Point((C.x + H.x) / 2, (C.y + H.y) / 2);
        drawPoint(g2, N_A, "");
        drawPoint(g2, N_B, "");
        drawPoint(g2, N_C, "");

        g2.setColor(new Color(183, 189, 5));
        drawPoint(g2, O, "O");
        g2.setColor(new Color(183, 189, 5)); //dark yellow :))
        drawPoint(g2, H, "H");
        g2.setColor(new Color(183, 189, 5));
        drawPoint(g2, E, "E");
    }
        private Point calculateCircumcentru(Point A, Point B, Point C) {
            Point M_AB = new Point((A.x + B.x) / 2, (A.y + B.y) / 2);   //mijloacele laturilor
            Point M_BC = new Point((B.x + C.x) / 2, (B.y + C.y) / 2);

            double m_AB = (double) (B.y - A.y) / (B.x - A.x);   //panta laturilor
            double m_BC = (double) (C.y - B.y) / (C.x - B.x);
            double m_perpendicular_AB = -1 / m_AB;  //pantele perpendiculare
            double m_perpendicular_BC = -1 / m_BC;
            double b1 = M_AB.y - m_perpendicular_AB * M_AB.x;   //ecuatia mediatoarelor
            double b2 = M_BC.y - m_perpendicular_BC * M_BC.x;
            double x = (b2 - b1) / (m_perpendicular_AB - m_perpendicular_BC);   //punctul de intersectie al mediatoarelor
            double y = m_perpendicular_AB * x + b1;
            return new Point((int) x, (int) y);
        }
        private Point calculateOrtocentru(Point A, Point B, Point C) {
            double m_BC = (double) (C.y - B.y) / (C.x - B.x);   //calculam pantele laturilor
            double m_CA = (double) (A.y - C.y) / (A.x - C.x);
            double m_perpendicular_BC = -1 / m_BC; // Perpendiculara pe BC
            double m_perpendicular_CA = -1 / m_CA; //CA
            double b1 = A.y - m_perpendicular_BC * A.x; // Inaltimea din A
            double b2 = B.y - m_perpendicular_CA * B.x; // Inaltimea din B
            double x = (b2 - b1) / (m_perpendicular_BC - m_perpendicular_CA);   //intersectia inaltimilor
            double y = m_perpendicular_BC * x + b1;
            return new Point((int) x, (int) y);
        }
        private Point calculateHeight(Point A, Point B, Point C) {
            double m_BC = (double) (C.y - B.y) / (C.x - B.x); // Panta laturii BC
            double m_perpendicular = -1 / m_BC; // Panta înălțimii din A
            double b_perpendicular = A.y - m_perpendicular * A.x;   //ecuatia inaltimii din A
            double b_BC = B.y - m_BC * B.x; // ecuatia dreptei B
            double x = (b_BC - b_perpendicular) / (m_perpendicular - m_BC); //punctul de intersectie
            double y = m_BC * x + b_BC;
            return new Point((int) x, (int) y);
        }
        private double calculateDistance(Point p1, Point p2) { 
            return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        }


    public void showLaAlegere(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Point A = points.get(0);    //puncte triunghi principal
        Point B = points.get(1);
        Point C = points.get(2);
        g2.setColor(Color.BLACK);
        g2.drawLine(A.x, A.y, B.x, B.y);    //desenam triunghiul
        g2.drawLine(B.x, B.y, C.x, C.y);
        g2.drawLine(C.x, C.y, A.x, A.y);
        int n = 2; // numarul de niveluri fractale (modificabil)
        List<Triangle> triangles = new ArrayList<>();
        triangles.add(new Triangle(A, B, C)); // adaugam triunghiul principal

        for (int level = 0; level < n; level++) {
            List<Triangle> newTriangles = new ArrayList<>();
            for (Triangle triangle : triangles) {
                Point AB_mid = getMidPoint(triangle.A, triangle.B); //se imparte fiecare latura a triunghiului in doua puncte noi
                Point BC_mid = getMidPoint(triangle.B, triangle.C);
                Point CA_mid = getMidPoint(triangle.C, triangle.A);
                newTriangles.add(new Triangle(triangle.A, AB_mid, CA_mid)); //am generat cele 4 triunghiuri noi
                newTriangles.add(new Triangle(AB_mid, triangle.B, BC_mid));
                newTriangles.add(new Triangle(CA_mid, BC_mid, triangle.C));
                newTriangles.add(new Triangle(AB_mid, BC_mid, CA_mid));
            }
            triangles = newTriangles; // actualizam lista de triunghiuri
        }
        point1 = Double.MAX_VALUE;
        g2.setColor(Color.RED);
        for (Triangle triangle : triangles) {
            g2.drawLine(triangle.A.x, triangle.A.y, triangle.B.x, triangle.B.y);    //desenam toate triunghiurile generate
            g2.drawLine(triangle.B.x, triangle.B.y, triangle.C.x, triangle.C.y);
            g2.drawLine(triangle.C.x, triangle.C.y, triangle.A.x, triangle.A.y);
            double area = calculateTriangleArea(triangle.A, triangle.B, triangle.C);    //calculam aria triunghiului
            if (area < point1) {
                point1 = area; // Actualizam aria minima daca este mai mica
            }
        }
        drawTriunghi(g2);
    }
        public Point getMidPoint(Point p1, Point p2) {  //functia care obtine punctul mijlociu intre doua puncte
            return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        }
        public double calculateTriangleArea(Point A, Point B, Point C) {    //functia pentru calculul de arii
            return Math.abs((A.x * (B.y - C.y) + B.x * (C.y - A.y) + C.x * (A.y - B.y)) / 2.00);
        } 
        class Triangle {
            Point A, B, C;

            Triangle(Point A, Point B, Point C) {
                this.A = A;
                this.B = B;
                this.C = C;
            }
        }  
    
    private void drawPoint(Graphics2D g2, Point p, String label) {
        Color currentColor = g2.getColor();
        g2.fill(new Ellipse2D.Double(p.x - 5, p.y - 5, 10, 10));
        g2.setColor(Color.BLACK);
        g2.drawString(label, p.x + 10, p.y - 10);
        g2.setColor(currentColor);
    }
}