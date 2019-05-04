package Day15;

import java.util.Comparator;

class FewestHitpointsComparator implements Comparator<Being> {
    @Override
    public int compare(Being o1, Being o2) {
        return o1.HP - o2.HP;
    }
}
