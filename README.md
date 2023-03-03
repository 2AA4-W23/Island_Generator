# Assignment A2: Mesh Generator

  - Fiza Sehar [seharf@mcmaster.ca]
  - Sarim Zia [zias11@mcmaster.ca]
  - Hamzah Rawasia [rawash1@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with thefeature released in your project_

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
|  F01  |  Created segments, connected verticies and displayed them |  Hamzah,Sarim,Fiza     | 16th Feb  |  19th Feb   |    D    |
|  F02  |  Used mesh adt to display polygon grid  |  Hamzah,Sarim,Fiza     | 19th Feb  |  19th Feb   |    D    |
|  F03  |  Displayed mesh with colors, thickness and transparency information |  Fiza     | 21th Feb  | 25th Feb  |    D    |
|  F04  |  Added and displayed centroid location for polygon  |  Hamzah,Sarim     | 24th Feb  |  24th Feb   |    D    |
|  F05  |  Added debug mode to change display  |  Fiza     | 24th Feb  | 28th Feb  |    D    |
|  F06  |  Add irregular mesh class, and generate and display random points  |  Hamzah | 26th Feb  | 27th Feb  |    D    |
|  F07  |  Generated and displayed voronoi diagram  |  Hamzah, Sarim | Feb 28th  | March 1st  |    D    |
|  F08  |  Applied Lloyd relaxation, and visualized the relaxed mesh  |  Hamzah,Sarim | March 1st  | March 1st  |    D    |
|  F09  | Cropped the mesh to adjust visualization to fit within the grid dimensions  |  Fiza | March 1st | March 1st  |    D    |
|  F10  |             |  Fiza | March 1st | March 1st  |    D    |


