# آزمایشگاه مهندسی نرم‌افزار - گزارش آزمایش ششم
[لینک مستندات آزمایش ششم](https://github.com/ssc-public/Software-Engineering-Lab/blob/main/courseworks/experiments/patterns-and-refactoring.md)

## اعضای گروه:
امیرحسین حاجی محمد رضایی - 99109252

علی رازقندی - 99109296

سید‌رضا قمقام - 99170542

## گزارش فاز اول
در این بخش در ابتدا در مورد نحوه پیاده‌سازی دو الگو state و startegy برای پیاده کردن برنامه موردنظر اشاره کرده و بعد از آن مستند دستورات برنامه اصلی را توضیح می‌دهم. این سیستم با استفاده از روش TDD ایجاد شده است و تست‌های آن را در بخش tests می‌توانید مشاهده کنید.

### نحوه اعمال کردن الگو‌ها
#### الگو state
همانطور که می‌دانیم، گراف شهری در این مساله میتواند دارای دو حالت باشد، اینکه یا همه یال‌های آن دو جهته باشند و یا اینکه یک‌طرفه باشند. در اینجا این ویژگی‌ها را به‌عنوان استیت گراف شهری در نظر می‌گیرم و این ویژگی را با الگو state به‌صورت زیر پیاده می‌کنم:

```java
// RouteState.java
package graph;

public interface RouteState {
    void changeDirection(Graph graph, boolean directed);

    void changeTimeUnit(Graph graph, int unit);
}
```

در ابتدا یک اینترفیس برای استیت ایجاد می‌کنم که متود‌های مشترک و لازم برای هردو حالت را تعریف کند.
```java
// UnidirectionalState.java
package graph;

public class UnidirectionalState implements RouteState {
    @Override
    public void changeDirection(Graph graph, boolean directed) {
        for (Node node : graph.getGraph()) {
            for (Edge edge : node.getEdges()) {
                edge.setDirected(true);
            }
        }
    }

    @Override
    public void changeTimeUnit(Graph graph, int unit) {
        for (Node node : graph.getGraph()) {
            for (Edge edge : node.getEdges()) {
                edge.setWeight(unit);
            }
        }
    }
}
```

```java
// BidirectionalState.java
package graph;

public class BidirectionalState implements RouteState {
    @Override
    public void changeDirection(Graph graph, boolean directed) {
        for (Node node : graph.getGraph()) {
            for (Edge edge : node.getEdges()) {
                edge.setDirected(false);
            }
        }
    }

    @Override
    public void changeTimeUnit(Graph graph, int unit) {
        for (Node node : graph.getGraph()) {
            for (Edge edge : node.getEdges()) {
                edge.setWeight(unit);
            }
        }
    }
}
```

حال با استفاده از اینترفیس تعریف‌شه، دو کلاس جدا برای هراستیت تعریف می‌کنم. یکی برای یال‌های یک‌جهته و یکی برای یال‌های دو‌جهته. برای هرکلاس، یک متود changeDirection وجود دارد که جهت یال‌ها را با‌توجه به آن استیت تغییر می‌دهد و یک changeaTimeUnit وجود دارد که برای تغییر دادن واحد زمانی سفر قطار (وزن یال‌ گراف) می‌باشد.

```java
// RouteContext.java
package graph;

public class RouteContext {
    private RouteState state;

    public void setState(RouteState state) {
        this.state = state;
    }

    public void changeDirection(Graph graph, boolean directed) {
        state.changeDirection(graph, directed);
    }

    public void changeUnit(Graph graph, int unit) {
        state.changeTimeUnit(graph, unit);
    }
}
```

در آخر نیز یک کلاس RouteContext درنظر می‌گیرم که یک فیلد آن استیت مورد‌نظر است و با این امکان که میتوان استیت را عوض کرد و متود موردنظر را بدون توجه به نوع استیت بر آن اعمال کرد.

#### الگو strategy
طبق تعریف مساله، برای رفتن از یک شهر به شهر دیگر، دو راه با اتوبوس و یا قطار وجود دارد. با‌توجه به اینکه واحد زمانی برای حرکت اتوبوس ثابت است، در اینجا از الگوریتم bfs برای محاسبه زمان رسیدن به یک شهر با اتوبوس استفاده می‌کنیم و از الگوریتم dijkstra برای محاسبه زمان رسیدن با استفاده از قطار بهره می‌بریم. با‌توجه به اینکه در حین اجرا برنامه، ممکن است ما به هر دوی این روش‌ها برای محاسبه زمان رسیدن نیاز داشته باشیم، این ویژگی همانند استیت در بخش قبلی نیست و درنتیجه اینجا از الگو pattern برای پیاده‌سازی این ویژگی استفاده می‌کنم.

```java
// DistanceStrategy.java
package graph;

public interface DistanceStrategy {
    void calculateDistance(Node start, Node hate);
}
```

در اینجا مشابه بخش قبل یک اینترفیس برای تعریف متود محاسبه فاصله برای هر دو استراتژی ایجاد می‌کنم.
```java
// BfsStrategy.java
package graph;

public class BfsStrategy implements DistanceStrategy {
    private Graph graph;

    public BfsStrategy(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void calculateDistance(Node start, Node hate) {
        graph.bfs(start, hate);
    }
}
```

```java
// DijkstraStrategy.java
package graph;

public class DijkstraStrategy implements DistanceStrategy {
    private Graph graph;

    public DijkstraStrategy(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void calculateDistance(Node start, Node hate) {
        graph.dijkstra(start, hate);
    }
}
```

حال در اینجا برای هر روش یک کلاس ایجاد می‌کنم که متناسب با هر روش متود محاسبه فاصله آن بر آبجکت گراف محاسبه شود. در این صورت، برای پیاده‌سازی برنامه اصلی میتوانیم یک شی از کلاس هر روش داشته باشیم و از هردوی آنها همزمان برای محاسبه فاصله‌ها استفاده کنیم.


### دستورات برنامه اصلی 
در اینجا نحوه کار کردن با برنامه اصلی که در اینجا (در فایل Main.java) نوشته شده است را توضیح می‌دهم. با احرا برنامه، در ابتدا باید تعداد گره‌های گراف را مشخص کنید. بعد از تعیین آن، گره‌های با شماره ۱ تا n که تعداد گره است، ایجاد می‌شوند. بعد از آن باید تعداد یال‌ها را مشخص کنید و بعد،‌ به ازای هر یال شماره گره‌های انتهایی یال‌را مشخص کنید که باید به ازای هر یال، در یک خط شماره یال ابتدایی و انتهایی را با استفاده از شماره آنها تعیین کنید. مثال:

graph sample:
2----3
|    |
|    |
|    |
1    4

input sample:
4
3
1 2
2 3
3 4

بعد از اینکه گراف ایجاد شد، حال با استفاده از دستورات زیر می‌توان این درخواست‌ها را از سیستم داشت:
1. unidirectionalize all routes: یک طرفه کردن همه مسیرها
2. make all routes bidirectional: دو‌طرفه کردن همه مسیر‌ها
3. change train time unit: تغییر دادن واحد زمانی، بعد از وارد کردن این دستور، باید یک عدد ضحیح مثبت به‌عنوان واحد زمانی جدید وارد کنید.
4. distance by train: محاسبه فاصله زمانی با حرکت اتوبوس، بعد از وارد کردن این دستور باید شهر مبدا و مقصد مشخص شوند. مثال:
input example:
distance by train
1 4

5. distance by bus: محاسبه فاصله شهر با حرکت قطار. مشابه دستور قبلی با شهر مبدا و مقصد مشخص شوند.
6. fastest way to travel: با تعیین شهر مبدا و مقصد، میتواند مشخص کند با حرکت اتوبوس سریع‌تر به مقصد می‌رسید یا با قطار
7. possible to avoid city: با تعیین شهر مبدا و مقصد و شماره شهر مورد‌نفرت، نشان می‌دهد که آیا میتوان بدون عبور از شهر مورد‌نفرت به مقصد رسید یا نه. مثال ورودی:
input example:
possible to avoid city
1 2 3 (origin, dest, hated)
8. exit: خروج از برنامه
