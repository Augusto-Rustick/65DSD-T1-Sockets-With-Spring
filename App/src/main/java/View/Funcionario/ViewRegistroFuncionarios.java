package View.Funcionario;

import Components.DepartamentoComboBox;
import Socket.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class ViewRegistroFuncionarios extends JFrame{

    private final JTextField cpfField;
    private final JTextField nameField;
    private final JTextField addressField;
    private final JTextField ctpsField;
    private final DepartamentoComboBox departmentCb;

    public ViewRegistroFuncionarios(Client client) throws IOException {

        JLabel cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField(20);
        cpfField.setFont(cpfField.getFont().deriveFont(16f));
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        nameField.setFont(nameField.getFont().deriveFont(16f));
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(20);
        addressField.setFont(addressField.getFont().deriveFont(16f));
        JLabel ctpsLabel = new JLabel("CTPS:");
        ctpsField = new JTextField(20);
        ctpsField.setFont(addressField.getFont().deriveFont(16f));
        JLabel departmentCbLabel = new JLabel("Departamento:");
        departmentCb = new DepartamentoComboBox(client);
        departmentCb.setFont(departmentCb.getFont().deriveFont(16f));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(registerButton.getFont().deriveFont(16f));

        registerButton.addActionListener(e -> {
            String txt = "funcionario;INSERT;" + cpfField.getText() + ";" + nameField.getText() + ";" + addressField.getText() + ";" + ctpsField.getText() + ";0;"
                    + departmentCb.getId() + ";";
            System.out.println(txt);
            try {
                String retorno = client.write(txt);
                if(retorno.charAt(0) == '{'){
                    retorno = "Erro ao cadastrar, verifique os campos e tente novamente";
                }
                showMessageDialog(this, retorno);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(cpfLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(cpfField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(ctpsLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(ctpsField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(departmentCbLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(departmentCb, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(registerButton, gbc);

        getContentPane().setName("Registar funcionários");
        add(formPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
}
