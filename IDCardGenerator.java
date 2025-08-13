import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class IDCardGenerator extends JFrame{
    private final JTextField nameField = new JTextField(18);
    private final JTextField idField = new JTextField(18);
    private final JComboBox<String> deptField = new JComboBox<>(new String[] {
        "Computer Science", "Information Technology", "Electronics", "Mechanical", "Civil", "HR", "Finance", "Marketing"
    });
    private final JTextField addressField = new JTextField(18);
    private final JTextField bloodGroupField = new JTextField(18);
    private final PreviewPanel previewPanel = new PreviewPanel();

    public IDCardGenerator(){
        super("ID card Generator - Step 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(16,16));

        JPanel form = buildFormPanel() ;
        add(form,BorderLayout.WEST);
        add(previewPanel, BorderLayout.CENTER);

        wireLivePreview();
    }

    private JPanel buildFormPanel() {



        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(360, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 6, 6, 6);

        JLabel title = new JLabel("ID Card Details");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        JLabel fullNameLabel = new JLabel("Full Name");
        fullNameLabel.setFont(fullNameLabel.getFont().deriveFont(Font.PLAIN, 16f));
        panel.add(fullNameLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel idLabel = new JLabel("Employee / Student ID");
        idLabel.setFont(idLabel.getFont().deriveFont(Font.PLAIN, 16f));
        panel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel deptLabel = new JLabel("Department");
        deptLabel.setFont(deptLabel.getFont().deriveFont(Font.PLAIN, 16f));
        panel.add(deptLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(deptField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setFont(addressLabel.getFont().deriveFont(Font.PLAIN, 16f));
        panel.add(addressLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel bloodLabel = new JLabel("Blood Group");
        bloodLabel.setFont(bloodLabel.getFont().deriveFont(Font.PLAIN, 16f));
        panel.add(bloodLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(bloodGroupField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; gbc.insets = new Insets(16, 6, 6, 6);
        JLabel hint = new JLabel("Live preview updates as you type");
        hint.setForeground(new Color(80, 80, 80));
        hint.setFont(hint.getFont().deriveFont(Font.PLAIN, 14f));
        panel.add(hint, gbc);

        gbc.gridy++;
        JButton uploadPhotoButton = new JButton("Upload Photo");
        panel.add(uploadPhotoButton, gbc);

        uploadPhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(IDCardGenerator.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedImage image = ImageIO.read(file);
                    previewPanel.setPhoto(image);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(IDCardGenerator.this,
                            "Failed to load image: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridy++;
        JButton exportButton = new JButton("Export as PNG");
        panel.add(exportButton, gbc);

        exportButton.addActionListener(e -> {

            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            String address = addressField.getText().trim();
            String bloodGroup = bloodGroupField.getText().trim();
            Object dept = deptField.getSelectedItem();

            if (name.isEmpty() || id.isEmpty() || address.isEmpty() || bloodGroup.isEmpty() || dept == null) {
                JOptionPane.showMessageDialog(IDCardGenerator.this,
                        "Please fill in all fields before exporting.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            BufferedImage image = new BufferedImage(previewPanel.getWidth(), previewPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            previewPanel.paint(g2d);
            g2d.dispose();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save ID Card as PNG");
            int userSelection = fileChooser.showSaveDialog(IDCardGenerator.this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                // Ensure file has .png extension
                if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                    fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".png");
                }
                try {
                    ImageIO.write(image, "png", fileToSave);
                    JOptionPane.showMessageDialog(IDCardGenerator.this,
                            "Image saved successfully.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(IDCardGenerator.this,
                            "Failed to save image: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private void wireLivePreview() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePreview();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePreview();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePreview();
            }
        };
        nameField.getDocument().addDocumentListener(listener);
        idField.getDocument().addDocumentListener(listener);
        addressField.getDocument().addDocumentListener(listener);
        bloodGroupField.getDocument().addDocumentListener(listener);
        deptField.addActionListener(e -> {
            updatePreview();
        });
    }

    private void updatePreview() {
        previewPanel.setNameText(nameField.getText());
        previewPanel.setIdText(idField.getText());
        previewPanel.setDeptText((String) deptField.getSelectedItem());
        previewPanel.setAddressText(addressField.getText());
        previewPanel.setBloodGroupText(bloodGroupField.getText());
        previewPanel.repaint();
    }

    private static class PreviewPanel extends JPanel {
        private String name = "";
        private String id = "";
        private String dept = "";
        private String address = "";
        private String bloodGroup = "";
        private BufferedImage photo = null;
        private BufferedImage logo = null;

        PreviewPanel() {
            setPreferredSize(new Dimension(300, 200));
            setBackground(new Color(240, 240, 240));
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
            try {
                logo = ImageIO.read(new File("logo.jpeg"));
            } catch (IOException e) {

                logo = null;
            }
        }

        public void setNameText(String name) {
            this.name = name;
        }

        public void setIdText(String id) {
            this.id = id;
        }

        public void setDeptText(String dept) {
            this.dept = dept;
        }

        public void setAddressText(String address) {
            this.address = address;
        }

        public void setBloodGroupText(String bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public void setPhoto(BufferedImage image) {
            this.photo = image;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cardX = 20, cardY = 20;
            int cardW = getWidth() - 40, cardH = getHeight() - 40;


            GradientPaint gp = new GradientPaint(cardX, cardY, new Color(245, 247, 250),
                    cardX, cardY + cardH, new Color(230, 235, 240));
            g2.setPaint(gp);
            g2.fillRoundRect(cardX, cardY, cardW, cardH, 25, 25);


            int headerH = 50;
            g2.setColor(new Color(33, 150, 243));
            g2.fillRoundRect(cardX, cardY, cardW, headerH, 25, 25);
            g2.fillRect(cardX, cardY + headerH - 25, cardW, 25); 


            int logoSize = 40;
            int logoPaddingRight = 15;
            if (logo != null) {
                int logoX = cardX + cardW - logoSize - logoPaddingRight;
                int logoY = cardY + (headerH - logoSize) / 2;
                g2.drawImage(logo, logoX, logoY, logoSize, logoSize, null);
            }


            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
            int orgNameX = cardX + 20;
            int orgNameY = cardY + 32;
            g2.drawString("JavaFrame Labs", orgNameX, orgNameY);


            int photoSize = 120;
            int photoX = cardX + 20;
            int photoY = cardY + headerH + 20;

            if (photo != null) {
                g2.drawImage(photo, photoX, photoY, photoSize, photoSize, null);
            } else {
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(photoX, photoY, photoSize, photoSize);
                g2.setColor(Color.DARK_GRAY);
                g2.drawRect(photoX, photoY, photoSize, photoSize);
            }

            g2.setColor(Color.BLACK);
            int textX = photoX + photoSize + 20;
            int textStartY = photoY + 20;
            int lineHeight = 30;


            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18f));
            String nameLabel = "Name:  ";
            g2.drawString(nameLabel, textX, textStartY);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18f));
            g2.drawString(this.name, textX + g2.getFontMetrics().stringWidth(nameLabel), textStartY);


            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16f));
            String idLabel = "ID:  ";
            g2.drawString(idLabel, textX, textStartY + lineHeight);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16f));
            g2.drawString(this.id, textX + g2.getFontMetrics().stringWidth(idLabel), textStartY + lineHeight);


            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16f));
            String deptLabel = "Department:  ";
            g2.drawString(deptLabel, textX, textStartY + 2 * lineHeight);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16f));
            g2.drawString(this.dept, textX + g2.getFontMetrics().stringWidth(deptLabel), textStartY + 2 * lineHeight);


            String addressText = this.address;
            int maxCharsPerLine = 25;
            String line1 = addressText;
            String line2 = "";
            if (addressText.length() > maxCharsPerLine) {
                line1 = addressText.substring(0, maxCharsPerLine);
                line2 = addressText.substring(maxCharsPerLine);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16f));
            String addressLabel = "Address:  ";
            g2.drawString(addressLabel, textX, textStartY + 3 * lineHeight);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16f));
            int addressValueX = textX + g2.getFontMetrics().stringWidth(addressLabel);
            g2.drawString(line1, addressValueX, textStartY + 3 * lineHeight);
            if (!line2.isEmpty()) {
                g2.drawString(line2, addressValueX, textStartY + 4 * lineHeight);
            }


            int bloodGroupLine = line2.isEmpty() ? 4 : 5;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16f));
            String bloodLabel = "Blood Group:  ";
            g2.drawString(bloodLabel, textX, textStartY + bloodGroupLine * lineHeight);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16f));
            g2.drawString(this.bloodGroup, textX + g2.getFontMetrics().stringWidth(bloodLabel), textStartY + bloodGroupLine * lineHeight);


            String orgAddressLabel = "Address:";
            String orgAddressValue = "Dr. Vishnuvardhana Road Post, RNS Farms Rd, Channasandra, Rajarajeshwari Nagar, Bengaluru, Karnataka 560098";
            g2.setColor(new Color(60, 60, 60)); // subtle gray color

            Font fontPlain = g2.getFont().deriveFont(Font.PLAIN, 14f);
            Font fontBold = g2.getFont().deriveFont(Font.BOLD, 14f);
            FontMetrics fmPlain = g2.getFontMetrics(fontPlain);
            FontMetrics fmBold = g2.getFontMetrics(fontBold);
            int maxWidth = cardW - 40;
            java.util.List<java.util.List<java.awt.Font>> lineFonts = new java.util.ArrayList<>();
            java.util.List<java.util.List<String>> lineTexts = new java.util.ArrayList<>();


            StringBuilder valueBuilder = new StringBuilder();
            int labelWidth = fmBold.stringWidth(orgAddressLabel + " ");
            int availableWidth = maxWidth - labelWidth;
            int idx = 0;
            String[] valueWords = orgAddressValue.split(" ");
            StringBuilder testValue = new StringBuilder();
            while (idx < valueWords.length) {
                String next = testValue.length() == 0 ? valueWords[idx] : testValue + " " + valueWords[idx];
                if (fmPlain.stringWidth(next) <= availableWidth) {
                    testValue = new StringBuilder(next);
                    idx++;
                } else {
                    break;
                }
            }
            String firstLineValue = testValue.toString();

            java.util.List<String> firstLineTexts = new java.util.ArrayList<>();
            java.util.List<java.awt.Font> firstLineFonts = new java.util.ArrayList<>();
            firstLineTexts.add(orgAddressLabel + " ");
            firstLineFonts.add(fontBold);
            if (!firstLineValue.isEmpty()) {
                firstLineTexts.add(firstLineValue);
                firstLineFonts.add(fontPlain);
            }
            lineTexts.add(firstLineTexts);
            lineFonts.add(firstLineFonts);


            int valueStartIdx = idx;
            while (valueStartIdx < valueWords.length) {
                StringBuilder lineVal = new StringBuilder();
                int w = 0;
                while (valueStartIdx < valueWords.length) {
                    String next = lineVal.length() == 0 ? valueWords[valueStartIdx] : lineVal + " " + valueWords[valueStartIdx];
                    if (fmPlain.stringWidth(next) <= maxWidth) {
                        lineVal = new StringBuilder(next);
                        valueStartIdx++;
                    } else {
                        break;
                    }
                }
                java.util.List<String> lineT = new java.util.ArrayList<>();
                java.util.List<java.awt.Font> lineF = new java.util.ArrayList<>();
                lineT.add(lineVal.toString());
                lineF.add(fontPlain);
                lineTexts.add(lineT);
                lineFonts.add(lineF);
            }


            int lineHeightAddr = fmPlain.getHeight();
            int totalHeight = lineHeightAddr * lineTexts.size();
            int startY = cardY + cardH - 10 - (lineTexts.size() - 1) * lineHeightAddr;
            for (int i = 0; i < lineTexts.size(); i++) {

                int lineWidth = 0;
                for (int j = 0; j < lineTexts.get(i).size(); j++) {
                    Font f = lineFonts.get(i).get(j);
                    String t = lineTexts.get(i).get(j);
                    FontMetrics fmSeg = g2.getFontMetrics(f);
                    lineWidth += fmSeg.stringWidth(t);
                }
                int x = cardX + (cardW - lineWidth) / 2;
                int y = startY + i * lineHeightAddr;
                int segX = x;
                for (int j = 0; j < lineTexts.get(i).size(); j++) {
                    Font f = lineFonts.get(i).get(j);
                    String t = lineTexts.get(i).get(j);
                    g2.setFont(f);
                    FontMetrics fmSeg = g2.getFontMetrics(f);
                    g2.drawString(t, segX, y);
                    segX += fmSeg.stringWidth(t);
                }
            }

            g2.dispose();
        }
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{IDCardGenerator frame = new IDCardGenerator();
        frame.setVisible(true);});
    }
}