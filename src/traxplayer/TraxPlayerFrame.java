package traxplayer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.*;

public class TraxPlayerFrame extends javax.swing.JFrame implements ConfigListener {

    Mplayer audio;
    Mplayer video;
    float apos = 0;
    float mpos = 0;
    Config conf = new Config();
    File lastFile = new File(conf.getString(Config.confDefaultMediaFolder));

    public TraxPlayerFrame() {

        initComponents();
        audioOffset.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                audio.setOffset((Float) audioOffset.getValue());
            }
        });
        audioOffset.setEditor(new JSpinner.NumberEditor(audioOffset, "0.0"));
        balanceSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int bal = balanceSlider.getValue();
                int max = balanceSlider.getMaximum();
                audio.volume(bal * (200 / max));
                video.volume((max - bal) * (200 / max));
            }
        });
        initPlayers();
        this.setLocation(conf.getInt(Config.mainX), conf.getInt(Config.mainY));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                unload();
            }
        });
    }

    @Override
    public void configChanged(String key) {
        if (Config.confDefaultMediaFolder.equals(key)) {
            lastFile = new File(conf.getString(Config.confDefaultMediaFolder));
        } else if (Config.confPlayer.equals(key)) {
            initPlayers();
        }
    }

    @Override
    public void reset() {
        initPlayers();
    }

    private void initPlayers() {
        if (audio != null) {
            audio.kill();
        }
        audio = new Mplayer(conf, new MplayerListener() {
            @Override
            public void seek(float pos) {
                apos = pos;
                if (!syncToggle.isSelected()) {
                    audioOffset.setValue(apos - mpos);
                }
            }

            @Override
            public void pause() {
            }

            @Override
            public void speed(float spd) {
            }

            @Override
            public void killed() {
            }
        });
        audio.setFile(new File(audioFileField.getText()));
        if (video != null) {
            video.kill();
        }

        video = new Mplayer(conf, new MplayerListener() {
            @Override
            public void seek(float pos) {
                mpos = pos;
                if (syncToggle.isSelected()) {
                    //System.out.println(pos);
                    audio.seek(pos);
                } else {
                    audioOffset.setValue(apos - mpos);
                }
            }

            @Override
            public void pause() {
                if (syncToggle.isSelected()) {
                    audio.pause();
                }
            }

            @Override
            public void speed(float spd) {
                if (syncToggle.isSelected()) {
                    audio.speed(spd);
                }
            }

            @Override
            public void killed() {
                audio.kill();
            }
        });
        video.setFile(new File(videoFileField.getText()));
    }

    private void unload() {
        audio.kill();
        video.kill();
        conf.set(Config.mainX, this.getX());
        conf.set(Config.mainY, this.getY());
        conf.write();
    }

    @Override
    public void dispose() {
        unload();
        super.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        videoFileField = new javax.swing.JTextField();
        videoFileChoose = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        audioFileField = new javax.swing.JTextField();
        audioFileChoose = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        skipBack = new javax.swing.JButton();
        pauseAudio = new javax.swing.JButton();
        skipForward = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        audioOffset = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        pauseVideo = new javax.swing.JButton();
        syncToggle = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        balanceSlider = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TraxPlayer");
        setName("TraxPlayer"); // NOI18N

        jLabel1.setLabelFor(videoFileField);
        jLabel1.setText("Video:");

        videoFileField.setEditable(false);
        videoFileField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                videoFileFieldActionPerformed(evt);
            }
        });

        videoFileChoose.setText("Choose...");
        videoFileChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                videoFileChooseActionPerformed(evt);
            }
        });

        jLabel2.setLabelFor(audioFileField);
        jLabel2.setText("Audio track:");

        audioFileField.setEditable(false);

        audioFileChoose.setText("Choose...");
        audioFileChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audioFileChooseActionPerformed(evt);
            }
        });

        settingsButton.setText("Settings");
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Audio track", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_BOTTOM));

        skipBack.setText("<<");
        skipBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipBackActionPerformed(evt);
            }
        });

        pauseAudio.setText("Play/pause audio");
        pauseAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseAudioActionPerformed(evt);
            }
        });

        skipForward.setText(">>");
        skipForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipForwardActionPerformed(evt);
            }
        });

        jLabel3.setLabelFor(audioOffset);
        jLabel3.setText("Audio offset (sec)");

        audioOffset.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        audioOffset.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(audioOffset, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(skipBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pauseAudio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(skipForward))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pauseAudio)
                    .addComponent(skipBack)
                    .addComponent(skipForward))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(audioOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Video", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_BOTTOM));

        pauseVideo.setText("Play/pause video");
        pauseVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseVideoActionPerformed(evt);
            }
        });

        syncToggle.setText("Link audio track");
        syncToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncToggleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pauseVideo, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(syncToggle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pauseVideo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(syncToggle)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Audio balance", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_BOTTOM));

        balanceSlider.setMajorTickSpacing(50);
        balanceSlider.setMinorTickSpacing(25);
        balanceSlider.setPaintTicks(true);
        balanceSlider.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(balanceSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(balanceSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(videoFileField)
                            .addComponent(audioFileField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(videoFileChoose)
                            .addComponent(audioFileChoose)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(settingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(videoFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(videoFileChoose))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(audioFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(audioFileChoose))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(settingsButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void syncToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncToggleActionPerformed
        pauseAudio.setEnabled(!syncToggle.isSelected());
        audioOffset.setEnabled(syncToggle.isSelected());
        skipForward.setEnabled(!syncToggle.isSelected());
        skipBack.setEnabled(!syncToggle.isSelected());
    }//GEN-LAST:event_syncToggleActionPerformed

    private void videoFileChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_videoFileChooseActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(lastFile);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            lastFile = fc.getSelectedFile();
            videoFileField.setText(lastFile.getAbsolutePath());
        }
        video.setFile(lastFile);
    }//GEN-LAST:event_videoFileChooseActionPerformed

    private void audioFileChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audioFileChooseActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(lastFile);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            lastFile = fc.getSelectedFile();
            audioFileField.setText(lastFile.getAbsolutePath());
        }
        audio.setFile(lastFile);
    }//GEN-LAST:event_audioFileChooseActionPerformed

    private void pauseVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseVideoActionPerformed
        video.pause();
    }//GEN-LAST:event_pauseVideoActionPerformed

    private void pauseAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseAudioActionPerformed
        audio.pause();
    }//GEN-LAST:event_pauseAudioActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        ConfDialog.pop(this, conf);
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void skipForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipForwardActionPerformed
        audio.skip(10);
    }//GEN-LAST:event_skipForwardActionPerformed

    private void skipBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipBackActionPerformed
        audio.skip(-10);
    }//GEN-LAST:event_skipBackActionPerformed

    private void videoFileFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_videoFileFieldActionPerformed
    }//GEN-LAST:event_videoFileFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
               if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TraxPlayerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TraxPlayerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TraxPlayerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TraxPlayerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TraxPlayerFrame f = new TraxPlayerFrame();
                f.setVisible(true);
                f.conf.addListener(f);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton audioFileChoose;
    private javax.swing.JTextField audioFileField;
    private javax.swing.JSpinner audioOffset;
    private javax.swing.JSlider balanceSlider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton pauseAudio;
    private javax.swing.JButton pauseVideo;
    private javax.swing.JButton settingsButton;
    private javax.swing.JButton skipBack;
    private javax.swing.JButton skipForward;
    private javax.swing.JToggleButton syncToggle;
    private javax.swing.JButton videoFileChoose;
    private javax.swing.JTextField videoFileField;
    // End of variables declaration//GEN-END:variables
}
