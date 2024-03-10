package org.example.Pages;

import org.example.Classes.Episode;
import org.example.Classes.Series;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateSeasonPage extends JFrame implements ActionListener {
    JFrame frame;
    JButton submitButton;
    JTextField seasonNameTextField;
    TextArea episodesTextArea;
    Map<String, List<Episode>> seasons;
    public UpdateSeasonPage (Map<String, List<Episode>> sez,String seasonName, List<Episode> episodes){
        this.seasons=sez;
        frame = new JFrame("IMDb - Update Season");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 370);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setLayout(null);
        frame.setResizable(false);

        JLabel nameLabel = new JLabel("Season name: ");
        nameLabel.setBounds(25, 10, 100, 30);
        nameLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(nameLabel);

        seasonNameTextField = new JTextField(seasonName);
        seasonNameTextField.setBounds(125, 10, 200, 30);
        seasonNameTextField.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(seasonNameTextField);

        JLabel episodesLabel = new JLabel("Episodes: ");
        episodesLabel.setBounds(25, 50, 100, 30);
        episodesLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(episodesLabel);

        episodesTextArea = new TextArea();
        if(episodes!=null)
            for(Episode episode : episodes) {
                episodesTextArea.append("Name: " + episode.getName() + ", Duration: " + episode.getDuration() + "\n");
            }
        else
            episodesTextArea.append("Name: New Name Episode , Duration: New Duration\n");
        episodesTextArea.setBounds(25, 90, 300, 200);
        episodesTextArea.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(episodesTextArea);

        submitButton = new JButton("Submit");
        submitButton.setBounds(125, 300, 100, 30);
        submitButton.addActionListener(this);
        submitButton.setFocusable(false);
        frame.add(submitButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submitButton) {
            List<Episode> episodes = new ArrayList<>();
            String[] episodesList = episodesTextArea.getText().split("\n");
            for (String episode : episodesList) {
                String[] episodeDetails = episode.split(",");
                episodes.add(new Episode(episodeDetails[0].split(": ")[1], episodeDetails[1].split(": ")[1]));
            }
            seasons.put(seasonNameTextField.getText(), episodes);
            frame.dispose();
        }
    }
}
