package sample.model.Repository;

import java.util.List;

public interface Observer<C, T> {
    void onUpdate(C notify, T payload);
    void onUpdate(C notify, List<T> payload);

    class repoNotify implements Observable.notifyType<notifyEnum> {
        notifyEnum en;

        repoNotify(notifyEnum en) {
            this.en = en;
        }

        @Override
        public notifyEnum getType() {
            return en;
        }
    }

    enum notifyEnum{
        create, update, remove
    }
}
