package org.example.Pages;

import org.example.Classes.Actor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class UpdateActorPage extends JFrame implements ActionListener {
    JFrame frame;
    JButton submitButton;
    Actor actor;
    TextField nameTextField;
    TextArea bioTextArea;
    TextField performancesTextArea;

    public UpdateActorPage(Actor actor) {
        this.actor = actor;
        frame = new JFrame("IMDb - Update Actor");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setLayout(null);
        frame.setResizable(false);

        Label nameLabel = new Label("Name: ");
        nameLabel.setBounds(25, 10, 50, 30);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(nameLabel);
        nameTextField = new TextField(actor.getName());
        nameTextField.setBounds(85, 10, 200, 30);
        nameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(nameTextField);

        Label bioLabel = new Label("Biography: ");
        bioLabel.setBounds(25, 50, 70, 30);
        bioLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(bioLabel);
        bioTextArea = new TextArea(actor.getBiography());
        bioTextArea.setText(actor.getBiography());
        bioTextArea.setBounds(105, 50, 180, 100);
        bioTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(bioTextArea);

        Label performancesLabel = new Label("Number of Performances: ");
        performancesLabel.setBounds(25, 160, 180, 30);
        performancesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(performancesLabel);
        performancesTextArea = new TextField(String.valueOf(actor.getNumberOfPerformances()));
        performancesTextArea.setBounds(210, 160, 80, 30);
        performancesTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(performancesTextArea);

        submitButton = new JButton("Submit");
        submitButton.setBounds(25, 220, 300, 25);
        submitButton.addActionListener(this);
        frame.add(submitButton);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()== submitButton){
            frame.dispose();
            int performances;
            try {
                 performances = Integer.parseInt(performancesTextArea.getText());
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, "Wrong performace input", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int actorPerformances = actor.getNumberOfPerformances();
            for(int i = performances-1; i >= 0; i--){
                String name;
                String type;
                try {
                    name=this.actor.getPerformances().get(i).getProduction();
                    type=this.actor.getPerformances().get(i).getType();

                }
                catch (Exception IndexOutOfBoundsException){
                    name="";
                    type="";
                }

                new UpdateRolePage(name, type, actor, i);
            }
            for(int i = actorPerformances; i >0; i--)
                actor.getPerformances().remove(0);
            actor.setName(nameTextField.getText());
            actor.setBiography(bioTextArea.getText());
        }
    }
}
