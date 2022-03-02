package vase;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class GQueue {

    class QNode {
        AtomicBoolean locked = new AtomicBoolean(false);
        int threadID;
        QNode next = null;

        public QNode(int id) {
            this.threadID = id;
        }
    }

    AtomicReference<QNode> tail = new AtomicReference<>();

    public QNode lock(int id) {
        QNode qnode = new QNode(id);
        QNode pred = tail.getAndSet(qnode);

        // If we have a predecessor
        if (pred != null) {
            qnode.locked.compareAndSet(false, true);
            pred.next = qnode;
            while (qnode.locked.get()) {
            }
        }
        return qnode;
    }

    public void unlock(QNode qNode) {
        Vase.log(() -> {
            System.out.printf("%d starts to leave the Showroom...%n", qNode.threadID);
        });
        if (qNode.next == null) {
            Vase.log(() -> {
                System.out.printf("%d Detects no guest waiting behind in queue%n", qNode.threadID);
            });
            if (tail.compareAndSet(qNode, null)) {
                Vase.log(() -> {
                    System.out.printf("%d leaves the Showroom%n", qNode.threadID);
                });
                return;
            }
            Vase.log(() -> {
                System.out.printf("%d Detected Guest while trying to leave %n", qNode.threadID);
            });
            // Compare and set is unsuccessfull
            // Wait for update to occur
            Vase.log(() -> {
                System.out.printf("%d waits for expected guest to arrive in queue%n", qNode.threadID);
            });
            while (qNode.next == null) {
            }
        }

        Vase.log(() -> {
            System.out.printf("%d leaves the Showroom notifying %d on the way out%n", qNode.threadID,
                    qNode.next.threadID);
        });
        qNode.next.locked.set(false);

    }
}
