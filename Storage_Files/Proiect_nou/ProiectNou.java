import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.net.URL;

public class ProiectNou extends Frame {
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
        Font f = new Font("TimesRoman", 1, 14);
        public Button btnRedeseneaza, btnMediane, btnBisectoare, btnInaltimi, btnMediatoare, btnCerculEuler, btnLaAlegere;
    public static void main (String args[]){new ProiectNou();}
    
    public ProiectNou() {
        tool=getToolkit();

        Dimension res=tool.getScreenSize();        
        ww = res.width;
        hh = res.height;	
        setResizable(true);	                  
        setTitle("Desenează un triunghi");      
        setIconImage(tool.getImage(GetResources("images/ico.gif")));
        setBackground(new Color(0, 0, 0));
        setLayout(null);	
        loadImage(); 
        repaint();
        setExtendedState(Frame.NORMAL);
        setMinimumSize(new Dimension((ww/2), (hh/2))); // Ajustează aceste valori conform nevoilor tale
        leftPanel = new LeftPanel(this);
            add(leftPanel);
            leftPanel.setBounds(25, 50, 150,  (hh-100));

        drawSheet = new DrawSheet(this);
            add(drawSheet);
            drawSheet.setBounds(175, 50, (ww-175),  (hh-100));
            drawSheet.Desenare(); // Inițializăm evenimentele de mouse

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
            showMedianeFlag = false; // Resetează vizibilitatea medianelor
            showBisectoareFlag = false;
            showInaltimiFlag = false;
            showMediatoareFlag = false;
            showCerculEulerFlag = false;
            drawSheet.repaint();
        });

        btnMediane.addActionListener(e -> { 
            setTitle("Triunghi şi Mediană");
            showMedianeFlag = true;
            showBisectoareFlag = false;
            showInaltimiFlag = false;
            showMediatoareFlag = false;
            showCerculEulerFlag = false;
            drawSheet.repaint();
        });

        btnBisectoare.addActionListener(e -> {
            setTitle("Triunghi şi Bisectoare");
            showMedianeFlag = false; // Șterge medianele când se apasă alt buton
            showBisectoareFlag = true;
            showInaltimiFlag = false;
            showMediatoareFlag = false;
            showCerculEulerFlag = false;
            drawSheet.repaint();
        });

        btnInaltimi.addActionListener(e -> {
            setTitle("Triunghi şi Înălţimi");
            showMedianeFlag = false; // Șterge medianele când se apasă alt buton
            showBisectoareFlag = false;
            showInaltimiFlag = true;
            showMediatoareFlag = false;
            showCerculEulerFlag = false;
            drawSheet.repaint();
        });
        btnMediatoare.addActionListener(e -> {
            setTitle("Triunghi şi Mediatoare");
            showMedianeFlag = false; // Șterge medianele când se apasă alt buton
            showBisectoareFlag = false;
            showInaltimiFlag = false;
            showMediatoareFlag = true;
            showCerculEulerFlag = false;
            drawSheet.repaint();
        });
        btnCerculEuler.addActionListener(e -> {
            setTitle("Triunghi şi Cercul Euler");
            showMedianeFlag = false; // Șterge medianele când se apasă alt buton
            showBisectoareFlag = false;
            showInaltimiFlag = false;
            showMediatoareFlag = false;
            showCerculEulerFlag = true;
            drawSheet.repaint();
        });
        btnLaAlegere.addActionListener(e -> {
            setTitle("Triunghi şi EXcercuri");
            showMedianeFlag = false; // Șterge medianele când se apasă alt buton
            showBisectoareFlag = false;
            showInaltimiFlag = false;
            showMediatoareFlag = false;
            showCerculEulerFlag = false;
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
    public boolean showMedianeFlag = false; // ReferințăFlag pentru a controla vizibilitatea Medianelor
    public boolean showBisectoareFlag = false; // ReferințăFlag pentru a controla vizibilitatea Bisectoarelor
    public boolean showInaltimiFlag = false; // ReferințăFlag pentru a controla vizibilitatea Inaltimilor
    public boolean showMediatoareFlag = false;  // ReferințăFlag pentru a controla vizibilitatea Mediatoarelor
    public boolean showCerculEulerFlag = false; // ReferințăFlag pentru a controla vizibilitatea CerculuiEuler
    public double radius;
    public double point1;
    public double point2;
    public double point3;
    public double surface1;
    


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
    // repaint();
    if (points.size() >= 2) {
        drawTriunghi(g2);
        if (proiectNou.showMedianeFlag)     {showMediane(g2);        updateTextArea();}
        if (proiectNou.showBisectoareFlag)  {showBisectoare(g2);     updateTextArea();}
        if (proiectNou.showInaltimiFlag)    {showInaltimi(g2);       updateTextArea();}
        if (proiectNou.showMediatoareFlag)  {showMediatoare(g2);     updateTextArea();}
        if (proiectNou.showCerculEulerFlag) {showCerculEuler(g2);    updateTextArea();}
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
            
            if(proiectNou.showBisectoareFlag){
                proiectNou.ta.setText(String.format(
                    "Laturi:\n AB = %.2f\n BC = %.2f\n CA = %.2f\n Aria = %.2f\n r = %.2f\n AI = %.2f\n BI = %.2f\n CI = %.2f",a, b, c, area, radius, point1, point2, point3));
            }
            else if(proiectNou.showInaltimiFlag || proiectNou.showMedianeFlag){
                proiectNou.ta.setText(String.format(
                    "Laturi:\n AB = %.2f\n BC = %.2f\n CA = %.2f\n Aria = %.2f\n AA' = %.2f\n BB' = %.2f\n CC' = %.2f",a, b, c, area, point1, point2, point3));
            }
            else if(proiectNou.showMediatoareFlag){
                proiectNou.ta.setText(String.format(
                    "Laturi:\n AB = %.2f\n BC = %.2f\n CA = %.2f\n Aria = %.2f\n Raza = %.2f\n ",a, b, c, area, radius));
            }
            else if(proiectNou.showCerculEulerFlag){
                proiectNou.ta.setText(String.format(
                    "Laturi:\n AB = %.2f\n BC = %.2f\n CA = %.2f\n Aria = %.2f\n rE = %.2f\n OH = %.2f\n",a, b, c, area, radius, point1));
            }
            else{
                proiectNou.ta.setText(String.format(
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

    // Calculăm mijloacele laturilor
    Point M_AB = new Point((A.x + B.x) / 2, (A.y + B.y) / 2);
    Point M_BC = new Point((B.x + C.x) / 2, (B.y + C.y) / 2);
    Point M_CA = new Point((C.x + A.x) / 2, (C.y + A.y) / 2);

    // Desenăm punctele mijlocului laturilor
    g2.setColor(Color.YELLOW);
    drawPoint(g2, M_AB, "C'");
    drawPoint(g2, M_BC, "A'");
    drawPoint(g2, M_CA, "B'");

    // Desenăm medianele
    g2.setColor(Color.BLUE);
    g2.drawLine(A.x, A.y, M_BC.x, M_BC.y); // Mediană din A către mijlocul BC
    g2.drawLine(B.x, B.y, M_CA.x, M_CA.y); // Mediană din B către mijlocul CA
    g2.drawLine(C.x, C.y, M_AB.x, M_AB.y); // Mediană din C către mijlocul AB

    // Calculăm distanțele medianelor
    point1 = A.distance(M_BC); // Distanța din A până la mijlocul BC
    point2 = B.distance(M_CA); // Distanța din B până la mijlocul CA
    point3 = C.distance(M_AB); // Distanța din C până la mijlocul AB

    // Calculăm baricentrul (punctul de intersecție al medianelor)
    double Gx = (A.x + B.x + C.x) / 3.0;
    double Gy = (A.y + B.y + C.y) / 3.0;
    Point G = new Point((int) Gx, (int) Gy);

    // Desenăm baricentrul
    g2.setColor(Color.YELLOW);
    drawPoint(g2, G, "G");
}


public void showBisectoare(Graphics2D g2) {
    // Obținem punctele triunghiului
    Point A = points.get(0);
    Point B = points.get(1);
    Point C = points.get(2);

    // Calculăm lungimile laturilor triunghiului
    double a = B.distance(C); // Latura BC
    double b = C.distance(A); // Latura CA
    double c = A.distance(B); // Latura AB
    // Calculăm semiperimetrul
    double s = (a + b + c) / 2;
    // Calculăm aria triunghiului folosind formula lui Heron
    double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));

    // Calculăm incentru (centrul cercului înscris)
    Point incentru = calculateIncenter(A, B, C, a, b, c);
    // Raza cercului înscris
    radius = area / s;

    // Desenăm cercul înscris complet (colorat)
    g2.setColor(Color.GRAY);
    g2.fill(new Ellipse2D.Double(incentru.x - radius, incentru.y - radius, 2 * radius, 2 * radius));
    drawTriunghi(g2);
    // Desenăm bisectoarele (până la incentru)
    g2.setColor(Color.BLUE);
    g2.drawLine(A.x, A.y, incentru.x, incentru.y); // Bisectoarea din A
    g2.drawLine(B.x, B.y, incentru.x, incentru.y); // Bisectoarea din B
    g2.drawLine(C.x, C.y, incentru.x, incentru.y); // Bisectoarea din C

    // Desenăm incentru (punctul de intersecție al bisectoarelor)
    g2.setColor(Color.YELLOW);
    drawPoint(g2, incentru, "I"); // Punctul de intersecție al bisectoarelor

    // Calculăm distanțele de la incentru la laturile triunghiului
    point1 = distanceFromPointToLine(incentru, B, C);
    point2 = distanceFromPointToLine(incentru, C, A);
    point3 = distanceFromPointToLine(incentru, A, B);
}
    private Point calculateIncenter(Point A, Point B, Point C, double a, double b, double c) {   // Calcularea incentru (centrul cercului înscris)
        double ax = A.x, ay = A.y;
        double bx = B.x, by = B.y;
        double cx = C.x, cy = C.y;
    
        // Calcularea coordonatelor incentru
        double ix = (a * ax + b * bx + c * cx) / (a + b + c);
        double iy = (a * ay + b * by + c * cy) / (a + b + c);
    
        return new Point((int) ix, (int) iy);
    }
    private double distanceFromPointToLine(Point p, Point l1, Point l2) {   // Calcularea distanței de la un punct la o dreaptă
        double numerator = Math.abs((l2.y - l1.y) * p.x - (l2.x - l1.x) * p.y + l2.x * l1.y - l2.y * l1.x);
        double denominator = Math.sqrt(Math.pow(l2.y - l1.y, 2) + Math.pow(l2.x - l1.x, 2));
        return numerator / denominator;
    }


public void showInaltimi(Graphics2D g2) {
         // Obținem punctele triunghiului
    Point A = points.get(0);
    Point B = points.get(1);
    Point C = points.get(2);

    // Calculăm ecuațiile dreptelor pentru fiecare latură
    Line2D.Double laturaBC = new Line2D.Double(B, C);
    Line2D.Double laturaCA = new Line2D.Double(C, A);
    Line2D.Double laturaAB = new Line2D.Double(A, B);

    // Calculăm înălțimile
    Point H_A = calculatePerpendicular(A, laturaBC);
    Point H_B = calculatePerpendicular(B, laturaCA);
    Point H_C = calculatePerpendicular(C, laturaAB);

    // Calculăm ortocentrul (intersecția înălțimilor)
    Point ortocentru = calculateIntersection(H_A, A, H_B, B);
    // Trasează înălțimile
    g2.setColor(Color.BLUE);
    g2.drawLine(A.x, A.y, H_A.x, H_A.y);
    g2.drawLine(B.x, B.y, H_B.x, H_B.y);
    g2.drawLine(C.x, C.y, H_C.x, H_C.y);
    // Desenăm punctele în care înălțimile intersectează laturile
    drawPoint(g2, H_A, "A'");
    drawPoint(g2, H_B, "B'");
    drawPoint(g2, H_C, "C'");

    // Desenăm ortocentrul
    g2.setColor(Color.YELLOW);
    drawPoint(g2, ortocentru, "H");

    // Calculăm lungimile înălțimilor
    point1 = A.distance(H_A);
    point2 = B.distance(H_B);
    point3 = C.distance(H_C);
}
    private Point calculatePerpendicular(Point P, Line2D.Double line) { // Funcție pentru calculul punctului de intersecție dintre o perpendiculară și o dreaptă
        double x1 = line.getX1(), y1 = line.getY1();
        double x2 = line.getX2(), y2 = line.getY2();

        // Calculăm panta dreptei
        double slope = (y2 - y1) / (x2 - x1);

        // Panta perpendicularei este invers reciprocă
        double perpendicularSlope = -1 / slope;

        // Ecuația perpendicularei: y = perpendicularSlope * x + b
        double b = P.y - perpendicularSlope * P.x;

        // Punctul de intersecție dintre dreaptă și perpendiculară
        double intersectX = (b - (y1 - slope * x1)) / (slope - perpendicularSlope);
        double intersectY = slope * intersectX + (y1 - slope * x1);

        return new Point((int) intersectX, (int) intersectY);
    }
    private Point calculateIntersection(Point P1, Point P2, Point Q1, Point Q2) {   // Funcție pentru calculul punctului de intersecție al două drepte
        double a1 = P2.y - P1.y;
        double b1 = P1.x - P2.x;
        double c1 = a1 * P1.x + b1 * P1.y;

        double a2 = Q2.y - Q1.y;
        double b2 = Q1.x - Q2.x;
        double c2 = a2 * Q1.x + b2 * Q1.y;

        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            // Dreptele sunt paralele; returnăm un punct null
            return null;
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
    radius = circumcenter.distance(A); // Distanța de la circumcentru la unul din vârfuri

    // Desenăm circumcentrul
    g2.setColor(Color.YELLOW);
    drawPoint(g2, circumcenter, "O");

    // Desenăm cercul circumscris
    g2.setColor(Color.BLUE);
    g2.drawOval((int) (circumcenter.x - radius), (int) (circumcenter.y - radius),
                (int) (2 * radius), (int) (2 * radius));
    
    // Calculăm mijloacele laturilor
    Point M_AB = new Point((A.x + B.x) / 2, (A.y + B.y) / 2);
    Point M_BC = new Point((B.x + C.x) / 2, (B.y + C.y) / 2);
    Point M_CA = new Point((C.x + A.x) / 2, (C.y + A.y) / 2);

    // Desenăm punctele mijlocului laturilor
    g2.setColor(Color.YELLOW);
    drawPoint(g2, M_AB, "C'"); // Mijlocul AB
    drawPoint(g2, M_BC, "A'"); // Mijlocul BC
    drawPoint(g2, M_CA, "B'"); // Mijlocul CA

    // Desenăm mediatoarele (de la mijlocul laturilor către circumcentru)
    g2.setColor(Color.BLUE);
    g2.drawLine(M_AB.x, M_AB.y, circumcenter.x, circumcenter.y); // Mediatoarea AB
    g2.drawLine(M_BC.x, M_BC.y, circumcenter.x, circumcenter.y); // Mediatoarea BC
    g2.drawLine(M_CA.x, M_CA.y, circumcenter.x, circumcenter.y); // Mediatoarea CA
}

public void showCerculEuler(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    Point A = points.get(0);
    Point B = points.get(1);
    Point C = points.get(2);

    // 1. Calculăm mijloacele laturilor
    Point M_AB = new Point((A.x + B.x) / 2, (A.y + B.y) / 2);
    Point M_BC = new Point((B.x + C.x) / 2, (B.y + C.y) / 2);
    Point M_CA = new Point((C.x + A.x) / 2, (C.y + A.y) / 2);

    // 2. Calculăm circumcentrul (O)
    Point O = calculateCircumcentru(A, B, C);

    // 3. Calculăm ortocentrul (H)
    Point H = calculateOrtocentru(A, B, C);

    point1 = O.distance(H);

    // 4. Calculăm mijlocul segmentului OH (centrul cercului Euler - E)
    Point E = new Point((O.x + H.x) / 2, (O.y + H.y) / 2);

    // 5. Calculăm înălțimile și punctele de pe laturile opuse
    Point H_A = calculateHeight(A, B, C);
    Point H_B = calculateHeight(B, C, A);
    Point H_C = calculateHeight(C, A, B);

    // 6. Calculăm raza cercului Euler (jumătate din raza circumferinței triunghiului)
    radius = calculateDistance(E, M_AB);

    // 11. Desenăm cercul Euler
    g2.setColor(Color.GREEN);
    g2.drawOval((int) (E.x - radius), (int) (E.y - radius), (int) (2 * radius), (int) (2 * radius));
    g2.setColor(new Color(211, 211, 211)); // Gri deschis (Light Gray)
    g2.fillOval((int) (E.x - radius), (int) (E.y - radius), (int) (2 * radius), (int) (2 * radius));

    drawTriunghi(g2);
    
    // 7. Desenăm segmentele de la mijlocul laturilor la circumcentru (O)
    g2.setColor(Color.BLUE);
    g2.drawLine(M_AB.x, M_AB.y, O.x, O.y);
    g2.drawLine(M_BC.x, M_BC.y, O.x, O.y);
    g2.drawLine(M_CA.x, M_CA.y, O.x, O.y);

    // 8. Desenăm înălțimile
    g2.drawLine(A.x, A.y, H_A.x, H_A.y);
    g2.drawLine(B.x, B.y, H_B.x, H_B.y);
    g2.drawLine(C.x, C.y, H_C.x, H_C.y);

    // 9. Desenăm segmentul OH
    g2.setColor(Color.RED);
    g2.drawLine(O.x, O.y, H.x, H.y);

    // 12. Desenăm cele 9 puncte de pe cercul Euler
    g2.setColor(Color.YELLOW);
    drawPoint(g2, M_AB, "");
    drawPoint(g2, M_BC, "");
    drawPoint(g2, M_CA, "");
    drawPoint(g2, H_A, "");
    drawPoint(g2, H_B, "");
    drawPoint(g2, H_C, "");

    // Punctele mijlocii ale segmentelor dintre vârfuri și ortocentru
    Point N_A = new Point((A.x + H.x) / 2, (A.y + H.y) / 2);
    Point N_B = new Point((B.x + H.x) / 2, (B.y + H.y) / 2);
    Point N_C = new Point((C.x + H.x) / 2, (C.y + H.y) / 2);
    // drawPoint(g2, M_CA, "M_CA");
    drawPoint(g2, N_A, "");
    drawPoint(g2, N_B, "");
    drawPoint(g2, N_C, "");

    g2.setColor(new Color(183, 189, 5));
    drawPoint(g2, O, "O");
    g2.setColor(new Color(183, 189, 5)); //dark yellow :))
    drawPoint(g2, H, "H");
    // 10. Desenăm centrul cercului Euler (E) ca punct
    g2.setColor(new Color(183, 189, 5));
    // g2.fillOval(E.x - 3, E.y - 3, 6, 6);
    drawPoint(g2, E, "E");
}
private Point calculateCircumcentru(Point A, Point B, Point C) {
    // Mijloacele laturilor
    Point M_AB = new Point((A.x + B.x) / 2, (A.y + B.y) / 2);
    Point M_BC = new Point((B.x + C.x) / 2, (B.y + C.y) / 2);

    // Panta laturilor
    double m_AB = (double) (B.y - A.y) / (B.x - A.x);
    double m_BC = (double) (C.y - B.y) / (C.x - B.x);

    // Pantele perpendiculare
    double m_perpendicular_AB = -1 / m_AB;
    double m_perpendicular_BC = -1 / m_BC;

    // Ecuațiile mediatoarelor: y = mx + b
    double b1 = M_AB.y - m_perpendicular_AB * M_AB.x;
    double b2 = M_BC.y - m_perpendicular_BC * M_BC.x;

    // Punctul de intersecție al celor două mediatoare (Circumcentrul)
    double x = (b2 - b1) / (m_perpendicular_AB - m_perpendicular_BC);
    double y = m_perpendicular_AB * x + b1;

    return new Point((int) x, (int) y);
}
private Point calculateOrtocentru(Point A, Point B, Point C) {
    // Calculăm pantele laturilor BC și AC
    double m_BC = (double) (C.y - B.y) / (C.x - B.x);
    double m_CA = (double) (A.y - C.y) / (A.x - C.x);

    // Calculăm pantele perpendiculare pe laturi
    double m_perpendicular_BC = -1 / m_BC; // Perpendiculară pe BC
    double m_perpendicular_CA = -1 / m_CA; // Perpendiculară pe CA

    // Ecuațiile înălțimilor
    double b1 = A.y - m_perpendicular_BC * A.x; // Înălțimea din A
    double b2 = B.y - m_perpendicular_CA * B.x; // Înălțimea din B

    // Calculăm coordonatele ortocentrului (intersecția celor două înălțimi)
    double x = (b2 - b1) / (m_perpendicular_BC - m_perpendicular_CA);
    double y = m_perpendicular_BC * x + b1;

    return new Point((int) x, (int) y);
}
private Point calculateHeight(Point A, Point B, Point C) {
    // Calculăm panta laturii BC
    double m_BC = (double) (C.y - B.y) / (C.x - B.x); // Panta laturii BC
    
    // Calculăm panta perpendiculară pe latura BC
    double m_perpendicular = -1 / m_BC; // Panta înălțimii din A
    
    // Ecuația dreptei perpendiculare (înălțimea din A)
    double b_perpendicular = A.y - m_perpendicular * A.x;
    
    // Ecuația dreptei laturii BC
    double b_BC = B.y - m_BC * B.x;
    
    // Punctul de intersecție dintre dreapta BC și înălțimea din A
    double x = (b_BC - b_perpendicular) / (m_perpendicular - m_BC);
    double y = m_BC * x + b_BC;
    
    return new Point((int) x, (int) y);
}
private double calculateDistance(Point p1, Point p2) {  // Calculăm distanța între două puncte
    return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
}


    public void showLaAlegere() {
        // Implementare Construcție personalizată
    }


    private void drawPoint(Graphics2D g2, Point p, String label) {
        Color currentColor = g2.getColor();
        g2.fill(new Ellipse2D.Double(p.x - 5, p.y - 5, 10, 10));
        g2.setColor(Color.BLACK);
        g2.drawString(label, p.x + 10, p.y - 10);
        g2.setColor(currentColor);
    }
}


