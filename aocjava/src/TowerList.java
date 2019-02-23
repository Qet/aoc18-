import java.util.ArrayList;

public class TowerList {

    // The maximum number of towers (i.e the breadth)
    private int maxNumTowers;
    private ArrayList<ArrayList<Integer>> data = new ArrayList<>();

    public TowerList(int towerSize) {
        this.maxNumTowers = towerSize;
    }

    public void add(int pos, int value){
        /*
        add it at the pos position.
        shift other elements to the right if necessary.

        1) filling up the breadth. make a new arraylist with 1 element in it, in the right location.
        2) once the breath is filled, then add the item to the right tower. don't add more towers.
         */

        if (getNumTowers() < maxNumTowers){
            createAndAddTower(pos, value);
        }
        else{
            insertIntoExistingTower(pos, value);
        }

    }

    private void createAndAddTower(int towerIndex, int value){
        if (towerIndex > getNumTowers())
            throw new IndexOutOfBoundsException("tower index out of bounds");

        ArrayList<Integer> tower = new ArrayList<>();
        tower.add(value);
        data.add(towerIndex, tower);
    }

    private void insertIntoExistingTower(int pos, int value){
        TowerSearchResult res = findTower(pos);
        res.tower.add(res.pos, value);
    }

    private int getNumTowers(){
        return data.size();
    }

    private TowerSearchResult findTower(int pos){
        if (pos > size())
            throw new IndexOutOfBoundsException("inserting into existing tower - index out of bounds");

        if (pos == size()){
            TowerSearchResult ret = new TowerSearchResult();
            ArrayList<Integer> lastTower = data.get(data.size() - 1);
            ret.pos = lastTower.size();
            ret.tower = lastTower;
            return ret;
        }

        int curTowerPos = pos;

        for(ArrayList<Integer> curTower : data){
            int nextTowerPos = curTowerPos - curTower.size();
            if (nextTowerPos < 0){  //Then we have found which tower to add to.
                TowerSearchResult ret = new TowerSearchResult();
                ret.pos = curTowerPos;
                ret.tower = curTower;
                return ret;
            }

            curTowerPos = nextTowerPos;
        }

        throw new RuntimeException("shouldn't get to the end without finding a tower here...");
    }

    class TowerSearchResult{
        int pos;
        ArrayList<Integer> tower;
    }



    private ArrayList<Integer> makeTower(int value){
        ArrayList<Integer> ret = new ArrayList<>();
        ret.add(value);
        return ret;
    }

    public int size(){
        int ret = 0;
        for(ArrayList<Integer> tower: data){
            ret += tower.size();
        }
        return ret;
    }

    public void remove(int pos){
        TowerSearchResult res = findTower(pos);
        res.tower.remove(res.pos);
    }

    public Integer get(int pos){
        TowerSearchResult res = findTower(pos);
        return res.tower.get(res.pos);
    }

}


//size
//get
//add
//remove