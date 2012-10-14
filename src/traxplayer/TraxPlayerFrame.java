package traxplayer;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
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
        initConfPanel();
        
        Rectangle r = this.getBounds();
        r.setSize(r.width, confPanel.getLocation().y + this.getInsets().top);
        
        this.setBounds(r);
        
        
    }

    private void initConfPanel() {
        sensSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                conf.set(Config.confSensitivity, sensSpinner.getValue());
            }
        });
        sensSpinner.setValue(conf.getDouble(Config.confSensitivity));

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

        videoTrackLabel = new javax.swing.JLabel();
        videoFileField = new javax.swing.JTextField();
        videoFileChoose = new javax.swing.JButton();
        audioTrackLabel = new javax.swing.JLabel();
        audioFileField = new javax.swing.JTextField();
        audioFileChoose = new javax.swing.JButton();
        audioPanel = new javax.swing.JPanel();
        skipBack = new javax.swing.JButton();
        pauseAudio = new javax.swing.JButton();
        skipForward = new javax.swing.JButton();
        offsetLabel = new javax.swing.JLabel();
        audioOffset = new javax.swing.JSpinner();
        videoPanel = new javax.swing.JPanel();
        pauseVideo = new javax.swing.JButton();
        syncToggle = new javax.swing.JToggleButton();
        balancePanel = new javax.swing.JPanel();
        balanceSlider = new javax.swing.JSlider();
        settingsToggle = new javax.swing.JToggleButton();
        confPanel = new javax.swing.JPanel();
        defaultMediaFolder = new javax.swing.JTextField();
        sensSpinner = new javax.swing.JSpinner();
        mplayerPath = new javax.swing.JTextField();
        mplayerPathLabel = new javax.swing.JLabel();
        driftLabel = new javax.swing.JLabel();
        driftUnitLabel = new javax.swing.JLabel();
        defaultFolderLabel = new javax.swing.JLabel();
        resetButton = new javax.swing.JButton();
        versionValue = new javax.swing.JLabel();
        versionLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TraxPlayer");
        setName("TraxPlayer"); // NOI18N
        setResizable(false);

        videoTrackLabel.setLabelFor(videoFileField);
        videoTrackLabel.setText("Video:");

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

        audioTrackLabel.setLabelFor(audioFileField);
        audioTrackLabel.setText("Audio track:");

        audioFileField.setEditable(false);

        audioFileChoose.setText("Choose...");
        audioFileChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audioFileChooseActionPerformed(evt);
            }
        });

        audioPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Audio track", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_BOTTOM));

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

        offsetLabel.setLabelFor(audioOffset);
        offsetLabel.setText("Audio offset (sec)");

        audioOffset.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        audioOffset.setEnabled(false);

        javax.swing.GroupLayout audioPanelLayout = new javax.swing.GroupLayout(audioPanel);
        audioPanel.setLayout(audioPanelLayout);
        audioPanelLayout.setHorizontalGroup(
            audioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(audioPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(audioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, audioPanelLayout.createSequentialGroup()
                        .addComponent(offsetLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(audioOffset, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(audioPanelLayout.createSequentialGroup()
                        .addComponent(skipBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pauseAudio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(skipForward))))
        );
        audioPanelLayout.setVerticalGroup(
            audioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(audioPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(audioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pauseAudio)
                    .addComponent(skipBack)
                    .addComponent(skipForward))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(audioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(audioOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(offsetLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        videoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Video", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_BOTTOM));

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

        javax.swing.GroupLayout videoPanelLayout = new javax.swing.GroupLayout(videoPanel);
        videoPanel.setLayout(videoPanelLayout);
        videoPanelLayout.setHorizontalGroup(
            videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(videoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pauseVideo, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(syncToggle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        videoPanelLayout.setVerticalGroup(
            videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(videoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pauseVideo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(syncToggle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        balancePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Audio balance", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_BOTTOM));

        balanceSlider.setMajorTickSpacing(50);
        balanceSlider.setMinorTickSpacing(25);
        balanceSlider.setPaintTicks(true);
        balanceSlider.setToolTipText("");

        javax.swing.GroupLayout balancePanelLayout = new javax.swing.GroupLayout(balancePanel);
        balancePanel.setLayout(balancePanelLayout);
        balancePanelLayout.setHorizontalGroup(
            balancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(balanceSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
        );
        balancePanelLayout.setVerticalGroup(
            balancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, balancePanelLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(balanceSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        settingsToggle.setText("Settings");
        settingsToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsToggleActionPerformed(evt);
            }
        });

        defaultMediaFolder.setText(conf.getString(Config.confDefaultMediaFolder));
        defaultMediaFolder.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                defaultMediaFolderFocusLost(evt);
            }
        });
        defaultMediaFolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                defaultMediaFolderKeyPressed(evt);
            }
        });

        sensSpinner.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.5d), Double.valueOf(0.1d), null, Double.valueOf(0.1d)));

        mplayerPath.setText(conf.getString(Config.confPlayer));
        mplayerPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                mplayerPathFocusLost(evt);
            }
        });
        mplayerPath.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mplayerPathKeyPressed(evt);
            }
        });

        mplayerPathLabel.setText("Mplayer path");

        driftLabel.setText("Link drift sensitivity");

        driftUnitLabel.setText("seconds");

        defaultFolderLabel.setText("Default folder");

        resetButton.setText("Reset players");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        versionValue.setText(conf.version + " b" + conf.build);

        versionLabel.setLabelFor(versionValue);
        versionLabel.setText("Version ");

        javax.swing.GroupLayout confPanelLayout = new javax.swing.GroupLayout(confPanel);
        confPanel.setLayout(confPanelLayout);
        confPanelLayout.setHorizontalGroup(
            confPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confPanelLayout.createSequentialGroup()
                .addGroup(confPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(driftLabel)
                    .addComponent(defaultFolderLabel)
                    .addComponent(mplayerPathLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(confPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mplayerPath)
                    .addComponent(defaultMediaFolder)
                    .addGroup(confPanelLayout.createSequentialGroup()
                        .addComponent(sensSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(driftUnitLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(confPanelLayout.createSequentialGroup()
                .addComponent(resetButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(versionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(versionValue)
                .addContainerGap())
        );
        confPanelLayout.setVerticalGroup(
            confPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(confPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mplayerPathLabel)
                    .addComponent(mplayerPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(confPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(defaultFolderLabel)
                    .addComponent(defaultMediaFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(confPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(confPanelLayout.createSequentialGroup()
                        .addGroup(confPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(driftLabel)
                            .addComponent(sensSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(driftUnitLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetButton))
                    .addGroup(confPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(versionValue)
                        .addComponent(versionLabel)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(audioTrackLabel)
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
                            .addComponent(confPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(videoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(balancePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(audioPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(videoTrackLabel)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(settingsToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(videoTrackLabel)
                    .addComponent(videoFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(videoFileChoose))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(audioTrackLabel)
                    .addComponent(audioFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(audioFileChoose))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(videoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(audioPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(balancePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(settingsToggle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void skipForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipForwardActionPerformed
        audio.skip(10);
    }//GEN-LAST:event_skipForwardActionPerformed

    private void skipBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipBackActionPerformed
        audio.skip(-10);
    }//GEN-LAST:event_skipBackActionPerformed

    private void videoFileFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_videoFileFieldActionPerformed
    }//GEN-LAST:event_videoFileFieldActionPerformed

    private void settingsToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsToggleActionPerformed
        Rectangle r = this.getBounds();
        r.setSize(r.width,
                  settingsToggle.isSelected()
                ? r.height + confPanel.getHeight()
                : r.height - confPanel.getHeight());
        this.setBounds(r);
    }//GEN-LAST:event_settingsToggleActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        conf.reset();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void defaultMediaFolderFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_defaultMediaFolderFocusLost
        conf.set(Config.confDefaultMediaFolder, defaultMediaFolder.getText());
    }//GEN-LAST:event_defaultMediaFolderFocusLost

    private void mplayerPathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mplayerPathFocusLost
        conf.set(Config.confPlayer, mplayerPath.getText());
    }//GEN-LAST:event_mplayerPathFocusLost

    private void mplayerPathKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mplayerPathKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            conf.set(Config.confPlayer, mplayerPath.getText());
        }
    }//GEN-LAST:event_mplayerPathKeyPressed

    private void defaultMediaFolderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_defaultMediaFolderKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            conf.set(Config.confDefaultMediaFolder, defaultMediaFolder.getText());
        }
    }//GEN-LAST:event_defaultMediaFolderKeyPressed

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
    private javax.swing.JPanel audioPanel;
    private javax.swing.JLabel audioTrackLabel;
    private javax.swing.JPanel balancePanel;
    private javax.swing.JSlider balanceSlider;
    private javax.swing.JPanel confPanel;
    private javax.swing.JLabel defaultFolderLabel;
    private javax.swing.JTextField defaultMediaFolder;
    private javax.swing.JLabel driftLabel;
    private javax.swing.JLabel driftUnitLabel;
    private javax.swing.JTextField mplayerPath;
    private javax.swing.JLabel mplayerPathLabel;
    private javax.swing.JLabel offsetLabel;
    private javax.swing.JButton pauseAudio;
    private javax.swing.JButton pauseVideo;
    private javax.swing.JButton resetButton;
    private javax.swing.JSpinner sensSpinner;
    private javax.swing.JToggleButton settingsToggle;
    private javax.swing.JButton skipBack;
    private javax.swing.JButton skipForward;
    private javax.swing.JToggleButton syncToggle;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JLabel versionValue;
    private javax.swing.JButton videoFileChoose;
    private javax.swing.JTextField videoFileField;
    private javax.swing.JPanel videoPanel;
    private javax.swing.JLabel videoTrackLabel;
    // End of variables declaration//GEN-END:variables
}
