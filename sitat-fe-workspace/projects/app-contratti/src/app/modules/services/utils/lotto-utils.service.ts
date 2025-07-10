import { LottoEntry } from '../../models/gare/gare.model';

export class LottoUtilsService {

    public static isCigMaster(lotto: LottoEntry): boolean {
        return lotto != null ? lotto.cig == lotto.masterCig : false;
    }

    public static isCigFiglio(lotto: LottoEntry): boolean {
        return lotto != null ? lotto.masterCig != null && lotto.cig != lotto.masterCig : false;
    }

}