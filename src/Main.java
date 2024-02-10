import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sand Simulation");
        SandSim sim = new SandSim(900, 600); // Set the size of the simulation
        frame.add(sim);
        frame.pack(); // Adjust the frame size based on its content
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);

        sim.startSimulation(); // Start the simulation loop
    }
}