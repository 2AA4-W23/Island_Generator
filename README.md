# Assignment A2: Mesh Generator

  - Fiza Sehar [seharf@mcmaster.ca]
  - Sarim Zia [zias11@mcmaster.ca]
  - Hamzah Rawasia [rawash1@mcmaster.ca]

## How to run the product

_Command line args:

Generator:
-t: Generate Irregular Mesh
-p: Enter Number of Polygons to be made (Only if you have chosen Irregular Mesh)
-r: Choose amount of times to perform relaxation on mesh (Only if you have chosen Irregular Mesh)
-dim: Choose the dimensions of the grid (Only if you have chosen Grid Mesh)

Example uses:
java -jar generator.jar sample.mesh -t -p 25 -r 5    -> Generates an irregular mesh with 25 polygons, and performs 5 relaxations
java -jar generator.jar sample.mesh -dim 100  -> Generates a grid mesh with grid dimensions of 100x100

Visualizer:
-t: Choose if you are generating an Irregular Mesh
-d: Generate the mesh in debug mode

Example uses:
java -jar visualizer.jar ../generator/sample.mesh sample.svg  -t   -> Use if you want to visualize an irregular mesh, without debug mode
java -jar visualizer.jar ../generator/sample.mesh sample.svg -d   -> Use if you want to visualize a grid mesh in debug mode

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one. 

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

```
mosser@azrael A2 % cd generator 
mosser@azrael generator % java -jar generator.jar sample.mesh
mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator % 
```

### Visualizer

To visualize an existing mesh, go the the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

```
mosser@azrael A2 % cd visualizer 
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```
To viualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tool slike `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

A feature is considered done when it is tested

### Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
|  F01  |  Create a circular island composed of land only, and visualize the mesh |  Hamzah    | 27th Feb  |  Mar 15th   |    D    |
|  F02  |  Introduce the inner circle, as well as oceans and lagoonsh |  sarim    | 27th Feb  | Mar 15th  |    D    |
|  F03  |  Mark land tiles which touch the water as beaches and visualize this change |  sarim    | 27th Feb  |   Mar 15th  |    D    |
|  F04  |  Allow a user to choose the shape of the island to be generated, and visualize these islands |  Fiza    |  Mar 15th  |   Mar 15th   |    D    |
|  F05  |  Provide the option to define altimetric profiles to visualize peaks, plateaus, fields, valleys, etc. |  Hamzah    | Mar 23th  |  Mar 26th   |    D    |
|  F06  |  Generate and visualize lakes in the island, which can cover one or more tilese |  Hamzah    | Mar 15th  |  Mar 22th   |    D    |
| ~~F07~~ | ~~Visualize the changes to vegetation as a result of the nearby lakes~~ | ~~hamzah~~ | ~~21th Feb~~ | ~~21th Feb~~ | ~~D~~ |
|  F08  |  Introduce rivers which can be visualized, and bring humidity to lands around them |  Fiza, sarim    | Mar 22th  |  March 29th   |    D    |
|  F09  |  Allow the user to choose how many rivers will be displayed in the island using a command line argument |  Hamzah    | March 23th |  March 23th   |    D    |
|  F10  | Accumulate the discharge of rivers that merge and display the rivers as one combined river that is thicker. Account for the moisture the river brings based on the thickness of the river |  sarim,fiza    | march 25th  |  march 29th   |    D    |
|  F11  |  Provide the option to randomly generate the amount of aquifer. Based on this generate the aquifers and generate the moisture to the surrounding tiles. |  sarim    | Mar 26th  |  Mar 26th  |    D    |
|  F12  |  Provide the option to choose the absorption profile. Based on that generate the ability for the soil to absorb humidity from lakes, rivers, and aquifers. |  Hamzah,Fiza,Sarim    | March 26th  |  April 1ST   |    D    |
|  F13 |  Assign biomes to each tile and represent it with different colours based on the level of humidity and temperature. |  sarim    | Mar 26th  |  Mar 26th   |    D    |
|  F14 |  Provide the option to select a Whitaker diagram, and then to generate the biome based on the Whitaker diagram. |  Fiza    | Mar 26th |  April 2nd   |    D    |
|  F15  |  Provide the option to provide the seed of the random generator that will control randomness so that all the decisions taken are the same when regenerating. |  Fiza    | April 2nd  |  April 2nd   |    D    |
