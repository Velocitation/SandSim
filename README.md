# Sand Simulation Java Code

## Overview
This Java code implements a simple sand simulation using Java Swing for the graphical user interface. It features a grid where particles (simulated as sand) can be added with mouse clicks and dragged across the panel. The simulation includes basic physics, such as gravity, to animate the sand particles.

## Features

- **Interactive Sand Creation**: Users can add sand to the simulation by clicking or dragging the mouse over the panel.
- **Gravity Simulation**: Sand particles are affected by gravity and will fall to the bottom of the panel, stacking upon one another or spreading out if possible.
- **Color Variation**: Sand particles change color as they are added to the simulation, providing a visually dynamic experience.

## How It Works

- **Grid System**: The simulation space is divided into a grid, with each cell potentially containing a sand particle.
- **Particle Movement**: Particles try to move downwards due to gravity. If the cell directly below is occupied, the particles will try to move diagonally down.
- **Velocity and Gravity**: Each particle has an associated velocity, influenced by gravity, which affects its movement.
