package Wattos.CloneWars.Gui;
/*
 * WattosParameterSetDialog.java
 *
 * Created on April 5, 2006, 3:58 PM
 */

/**
 *
 * @author  jurgen
 */
public class WattosParameterSetDialog_1 extends javax.swing.JDialog {
    private static final long serialVersionUID = -7143013293514007087L;
    /** Creates new form WattosParameterSetDialog */
    public WattosParameterSetDialog_1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelMain = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonEnter = new javax.swing.JButton();
        jPanelParameter1 = new javax.swing.JPanel();
        jPanelParameter2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jButtonCancel.setText("Cansel");

        jButtonEnter.setText("Enter");
        jButtonEnter.setEnabled(false);

        jPanelParameter1.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameter 1"));
        org.jdesktop.layout.GroupLayout jPanelParameter1Layout = new org.jdesktop.layout.GroupLayout(jPanelParameter1);
        jPanelParameter1.setLayout(jPanelParameter1Layout);

        jPanelParameter2.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameter 2"));
        org.jdesktop.layout.GroupLayout jPanelParameter2Layout = new org.jdesktop.layout.GroupLayout(jPanelParameter2);
        jPanelParameter2.setLayout(jPanelParameter2Layout);

        org.jdesktop.layout.GroupLayout jPanelMainLayout = new org.jdesktop.layout.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelMainLayout.createSequentialGroup()
                        .add(jButtonEnter)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonCancel))
                    .add(jPanelParameter1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanelParameter2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanelParameter2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanelMainLayout.linkSize(new java.awt.Component[] {jButtonCancel, jButtonEnter}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelParameter1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelParameter2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelParameter2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonCancel)
                    .add(jButtonEnter)) 
                .addContainerGap())
        );
        jScrollPane1.setViewportView(jPanelMain);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1)
        );
        pack();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WattosParameterSetDialog_1(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }
    
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonEnter;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelParameter1;
    private javax.swing.JPanel jPanelParameter2;
    private javax.swing.JScrollPane jScrollPane1;    
}
