package engine;

interface Fragment {
    abstract String findNeighbor();
    abstract void merge(Fragment f);
    abstract String print();
}
