package com.example.pro;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class ResumeATSPro extends JFrame {
    private JTextArea resumeTextArea;
    private JTextArea jobDescriptionTextArea;
    private JButton uploadResumeButton;
    private JButton submitButton;
    private JLabel atsScoreLabel;
    private JLabel fileInfoLabel;
    private JProgressBar progressBar;
    private String currentUser;

    private static final Set<String> STOP_WORDS = Set.of(
            "the", "and", "for", "are", "but", "not", "you", "with", "this", "that",
            "from", "they", "have", "has", "had", "will", "would", "can", "could",
            "our", "their", "what", "which", "when", "where", "who", "whom", "your",
            "all", "any", "there", "were", "been", "was", "his", "her", "him", "she",
            "himself", "herself", "them", "then", "than", "also", "its",
            "about", "into", "some", "more", "other", "such", "only", "own", "same",
            "so", "too", "very"
    );

    public ResumeATSPro(String username) {
        this.currentUser = username;
        setupUI();
        createLayout();
    }

    private void setupUI() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle(" Resume ATS Pro - Welcome, " + currentUser);
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(45, 45, 48));
    }

    private void createLayout() {
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(60, 63, 65));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel(" Advanced Resume ATS Scanner");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JButton logoutButton = new JButton(" Logout (" + currentUser + ")");
        logoutButton.setBackground(new Color(244, 67, 54));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> logout());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBackground(new Color(45, 45, 48));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Resume Panel
        JPanel resumePanel = createResumePanel();
        mainPanel.add(resumePanel);

        // Job Description Panel
        JPanel jobDescPanel = createJobDescPanel();
        mainPanel.add(jobDescPanel);

        return mainPanel;
    }

    private JPanel createResumePanel() {
        JPanel resumePanel = new JPanel(new BorderLayout());
        resumePanel.setBackground(new Color(60, 63, 65));
        resumePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(86, 156, 214), 2),
                " Resume Content"));

        // File info label
        fileInfoLabel = new JLabel("No file selected");
        fileInfoLabel.setForeground(Color.YELLOW);
        fileInfoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Resume text area
        resumeTextArea = new JTextArea();
        resumeTextArea.setEditable(false);
        resumeTextArea.setBackground(new Color(30, 30, 30));
        resumeTextArea.setForeground(Color.WHITE);
        resumeTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane resumeScroll = new JScrollPane(resumeTextArea);
        resumeScroll.setBorder(BorderFactory.createLineBorder(new Color(86, 156, 214)));

        // Upload button
        uploadResumeButton = new JButton(" Upload Resume (TXT Recommended)");
        uploadResumeButton.setBackground(new Color(0, 122, 204));
        uploadResumeButton.setForeground(Color.WHITE);
        uploadResumeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        uploadResumeButton.addActionListener(this::handleUploadResume);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(60, 63, 65));
        buttonPanel.add(uploadResumeButton);

        resumePanel.add(fileInfoLabel, BorderLayout.NORTH);
        resumePanel.add(resumeScroll, BorderLayout.CENTER);
        resumePanel.add(buttonPanel, BorderLayout.SOUTH);

        return resumePanel;
    }

    private JPanel createJobDescPanel() {
        JPanel jobDescPanel = new JPanel(new BorderLayout());
        jobDescPanel.setBackground(new Color(60, 63, 65));
        jobDescPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(86, 156, 214), 2),
                " Job Description"));

        jobDescriptionTextArea = new JTextArea();
        jobDescriptionTextArea.setBackground(new Color(30, 30, 30));
        jobDescriptionTextArea.setForeground(Color.WHITE);
        jobDescriptionTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jobDescriptionTextArea.setText("Paste the job description here...");
        JScrollPane jobDescScroll = new JScrollPane(jobDescriptionTextArea);
        jobDescScroll.setBorder(BorderFactory.createLineBorder(new Color(86, 156, 214)));

        jobDescPanel.add(jobDescScroll, BorderLayout.CENTER);

        return jobDescPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(60, 63, 65));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Progress bar
        progressBar = new JProgressBar();
        progressBar.setVisible(false);
        progressBar.setStringPainted(true);

        // Submit button
        submitButton = new JButton(" Calculate ATS Score");
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitButton.addActionListener(this::handleCalculateScore);

        // Score label
        atsScoreLabel = new JLabel("ATS Score: 0%", JLabel.CENTER);
        atsScoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        atsScoreLabel.setOpaque(true);
        atsScoreLabel.setBackground(Color.DARK_GRAY);
        atsScoreLabel.setForeground(Color.WHITE);
        atsScoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(60, 63, 65));
        buttonPanel.add(submitButton);

        footerPanel.add(progressBar, BorderLayout.NORTH);
        footerPanel.add(buttonPanel, BorderLayout.WEST);
        footerPanel.add(atsScoreLabel, BorderLayout.EAST);

        return footerPanel;
    }

    private void handleUploadResume(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Resume File");
        
        // Add file filter for supported formats
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Resume Files (TXT, PDF, DOCX, DOC)", "txt", "pdf", "docx", "doc"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            parseResumeWithProgress(file);
        }
    }

    private void parseResumeWithProgress(File file) {
        progressBar.setVisible(true);
        progressBar.setValue(0);
        
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                progressBar.setString("Reading file...");
                setProgress(30);
                
                String parsedText = parseResume(file.getAbsolutePath());
                
                progressBar.setString("Processing content...");
                setProgress(80);
                
                Thread.sleep(500); // Simulate processing
                return parsedText;
            }
            
            @Override
            protected void done() {
                try {
                    String parsedText = get();
                    resumeTextArea.setText(parsedText);
                    fileInfoLabel.setText(" " + file.getName() + " | " + 
                            getFileType(file.getName()) + " | " + 
                            (file.length() / 1024) + " KB");
                    progressBar.setString("Complete!");
                    progressBar.setValue(100);
                    
                    // Hide progress bar after 2 seconds
                    Timer timer = new Timer(2000, ev -> progressBar.setVisible(false));
                    timer.setRepeats(false);
                    timer.start();
                    
                } catch (Exception ex) {
                    showError("Error parsing file: " + ex.getMessage());
                    progressBar.setVisible(false);
                }
            }
        };
        
        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((Integer) evt.getNewValue());
            }
        });
        
        worker.execute();
    }

    private String getFileType(String fileName) {
        if (fileName.toLowerCase().endsWith(".pdf")) return "PDF";
        if (fileName.toLowerCase().endsWith(".docx")) return "DOCX";
        if (fileName.toLowerCase().endsWith(".doc")) return "DOC";
        if (fileName.toLowerCase().endsWith(".txt")) return "TXT";
        return "Unknown";
    }

    private void handleCalculateScore(ActionEvent e) {
        String resumeText = resumeTextArea.getText();
        String jobDescription = jobDescriptionTextArea.getText();
        
        if (resumeText.isEmpty() || resumeText.contains("Error") || resumeText.contains("Please upload")) {
            showError("Please upload a valid resume file first!");
            return;
        }
        
        if (jobDescription.isEmpty() || jobDescription.equals("Paste the job description here...")) {
            showError("Please enter a job description!");
            return;
        }
        
        progressBar.setVisible(true);
        progressBar.setString("Calculating score...");
        
        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() throws Exception {
                setProgress(50);
                return calculateScore(resumeText, jobDescription);
            }
            
            @Override
            protected void done() {
                try {
                    int atsScore = get();
                    updateScoreDisplay(atsScore);
                    progressBar.setVisible(false);
                    showScoreDetails(atsScore, resumeText, jobDescription);
                } catch (Exception ex) {
                    showError("Error calculating score: " + ex.getMessage());
                    progressBar.setVisible(false);
                }
            }
        };
        
        worker.execute();
    }

    private void updateScoreDisplay(int score) {
        atsScoreLabel.setText("ATS Score: " + score + "%");
        
        // Color coding based on score
        if (score >= 80) {
            atsScoreLabel.setBackground(new Color(76, 175, 80)); // Green
        } else if (score >= 60) {
            atsScoreLabel.setBackground(new Color(255, 193, 7)); // Yellow
        } else if (score >= 40) {
            atsScoreLabel.setBackground(new Color(255, 152, 0)); // Orange
        } else {
            atsScoreLabel.setBackground(new Color(244, 67, 54)); // Red
        }
    }

    private void showScoreDetails(int score, String resumeText, String jobDescription) {
        Set<String> resumeKeywords = extractKeywords(resumeText);
        Set<String> jobKeywords = extractKeywords(jobDescription);
        Set<String> commonKeywords = new HashSet<>(resumeKeywords);
        commonKeywords.retainAll(jobKeywords);
        
        String message = String.format(
            "<html><div style='width: 400px;'>" +
            "<h2>🎯 ATS Score Analysis</h2>" +
            "<div style='font-size: 24px; text-align: center; margin: 10px 0;'>" +
            "<b>%d%% Match</b></div>" +
            "<hr>" +
            "<b>📊 Statistics:</b><br>" +
            "• Resume Keywords: %d<br>" +
            "• Job Keywords: %d<br>" +
            "• Matching Keywords: %d<br><br>" +
            "<b>🔑 Top Matching Keywords:</b><br>%s<br>" +
            "<hr>" +
            "<b>💡 Tips:</b><br>%s" +
            "</div></html>",
            score,
            resumeKeywords.size(),
            jobKeywords.size(),
            commonKeywords.size(),
            getTopKeywords(commonKeywords, 10),
            getScoreTips(score)
        );
        
        JOptionPane.showMessageDialog(this, message, "ATS Score Results", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getTopKeywords(Set<String> keywords, int limit) {
        return String.join(", ", keywords.stream().limit(limit).toArray(String[]::new));
    }

    private String getScoreTips(int score) {
        if (score >= 80) return "🎉 Excellent! Your resume is well optimized for this job.";
        if (score >= 60) return "👍 Good match. Consider adding a few more relevant keywords.";
        if (score >= 40) return "⚠️ Average. Review the job description and add missing skills.";
        return "❌ Needs improvement. Major keyword gaps detected.";
    }

    // Simple file parsing - Works with TXT, fallback for others
    public String parseResume(String filePath) {
        try {
            File file = new File(filePath);
            String fileType = getFileType(filePath);
            long fileSizeKB = file.length() / 1024;
            
            // Only support TXT files for now (stable)
            if ("TXT".equals(fileType)) {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                content = content.replaceAll("\\s+", " ").trim();
                
                if (content.length() > 5000) {
                    content = content.substring(0, 5000) + "\n\n... (content truncated for display)";
                }
                
                return "✅ File: " + file.getName() + "\n" +
                       "📁 Type: " + fileType + "\n" +
                       "📊 Size: " + fileSizeKB + " KB\n" +
                       "📝 Parsed Content:\n\n" + content;
            }
            else {
                return "⚠️  File: " + file.getName() + "\n" +
                       "📁 Type: " + fileType + " (Limited Support)\n" +
                       "📊 Size: " + fileSizeKB + " KB\n" +
                       "💡 Status: PDF/DOCX support requires additional setup\n\n" +
                       "🔧 Quick Solutions:\n" +
                       "1. Convert your " + fileType + " to .txt format\n" +
                       "2. Copy-paste the content below manually\n" +
                       "3. Use a online PDF-to-TXT converter\n\n" +
                       "📋 For testing, create a .txt file with this content:\n" +
                       "Java Developer with Spring Boot experience\n" +
                       "MySQL database skills\n" +
                       "REST API development\n" +
                       "Git version control";
            }
                    
        } catch (Exception e) {
            return "❌ Error: " + e.getMessage() + 
                   "\n\n📋 Please:\n" +
                   "1. Use .txt files for now\n" +
                   "2. Ensure file is not open in another program\n" +
                   "3. Check file permissions";
        }
    }

    // Extract keywords from text
    public Set<String> extractKeywords(String text) {
        Set<String> keywords = new HashSet<>();
        String[] words = text.toLowerCase().split("\\W+");
        for (String word : words) {
            if (word.length() > 2 && !STOP_WORDS.contains(word)) {
                keywords.add(word);
            }
        }
        return keywords;
    }

    // Calculate ATS score
    public int calculateScore(String resumeText, String jobDescription) {
        Set<String> resumeKeywords = extractKeywords(resumeText);
        Set<String> jobKeywords = extractKeywords(jobDescription);

        if (jobKeywords.isEmpty()) return 0;

        Set<String> commonKeywords = new HashSet<>(resumeKeywords);
        commonKeywords.retainAll(jobKeywords);

        double score = ((double) commonKeywords.size() / jobKeywords.size()) * 100;
        return (int) Math.round(Math.min(score, 100));
    }

    private void logout() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", "Logout", 
            JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                LoginPagePro loginPage = new LoginPagePro();
                loginPage.setVisible(true);
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}