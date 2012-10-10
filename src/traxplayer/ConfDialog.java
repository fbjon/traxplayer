package traxplayer;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ConfDialog extends javax.swing.JDialog {

    private final Config conf;
    private static ConfDialog dialog;

    private ConfDialog(java.awt.Frame parent, final Config conf) {
        super(parent, false);
        initComponents();
        this.conf = conf;
        sensSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                conf.set(Config.confSensitivity, sensSpinner.getValue());
            }
        });
        sensSpinner.setValue(conf.getDouble(Config.confSensitivity));
        mplayerPath.setText(conf.getString(Config.confPlayer));
        defaultMediaFolder.setText(conf.getString(Config.confDefaultMediaFolder));
    }

    public static ConfDialog pop(java.awt.Frame parent, final Config conf) {
        if (dialog == null) {
            dialog = new ConfDialog(parent, conf);
            dialog.setLocationRelativeTo(parent);
        }
        dialog.setVisible(true);
        dialog.toFront();
        return dialog;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        mplayerPath = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        defaultMediaFolder = new javax.swing.JTextField();
        closeButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        resetButton = new javax.swing.JButton();
        sensSpinner = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();

        setTitle("Configuration");

        jLabel1.setText("Mplayer path");

        jLabel2.setText("Default folder");

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Link drift sensitivity");

        resetButton.setText("Reset players");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        sensSpinner.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.5d), Double.valueOf(0.1d), null, Double.valueOf(0.1d)));

        jLabel4.setText("seconds");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mplayerPath, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(defaultMediaFolder)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sensSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(resetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(mplayerPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(defaultMediaFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(sensSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(resetButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        conf.set(Config.confDefaultMediaFolder, defaultMediaFolder.getText());
        conf.set(Config.confPlayer, mplayerPath.getText());
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        conf.reset();
    }//GEN-LAST:event_resetButtonActionPerformed

    @Override
    public void dispose() {
        conf.write();
        super.setVisible(false);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField defaultMediaFolder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField mplayerPath;
    private javax.swing.JButton resetButton;
    private javax.swing.JSpinner sensSpinner;
    // End of variables declaration//GEN-END:variables
}
