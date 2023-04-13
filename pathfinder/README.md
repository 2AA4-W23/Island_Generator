# Assignment A4: Evolution

  - Hamzah Rawasia [rawash1@mcmaster.ca]

The Pathfinder package provides classes for working with graphs and finding paths between nodes using a pathfinder algorithm. It includes the following classes:

Graph
The Graph class represents a directed graph with nodes and edges. It provides methods for adding nodes 
and edges, getting the edges and nodes that it contains.

Node
The Node class represents a node in a graph. It has a unique identifier, can hold attributes and can be connected to other nodes through edges.

Edge
The Edge class represents a directed edge between two nodes in a graph. It has a source node, 
a target node, and can contain attributes.

Path
The Path class represents a contract for finding a path between nodes in a graph. It defines the methods
that will be used to calculate the path.

Pathfinder
The Pathfinder class provides methods for finding paths between nodes in a graph using a pathfinder algorithm, 
which in this case is a BFS algorithm that assumes that edges are unweighted in the graph. This means the 
shortest path is based on the number of edges traversed. It takes a Graph object as input and provides methods 
to find the shortest path between two nodes.

Extending the Library
The Pathfinder library can be extended by implementing new algorithms for finding paths between nodes. 
To add a new algorithm, you can create a new class that inherits from the Path interface and implements 
the algorithm logic. You can then add this class to the library and use it to find paths between nodes in 
a graph. One simply needs to ensure that naming conventions and signatures are matched with the interface.


Rationale
The Pathfinder library was developed to provide a flexible way of working with graphs 
and finding paths between nodes using different algorithms. It aims to be efficient and easy to use, 
with well-organized code. The library follows coding best practices and provides 
documentation to ensure readability, maintainability, and extensibility.

