package engine;

import java.util.ArrayList;

interface Fragment {
    abstract ArrayList<String> findNeighbor();
    abstract void merge(Fragment f);
    abstract String print();
}
