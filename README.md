<p align="center">
    <img width="400px" src="https://github.com/clos0710/Elevators/blob/master/img/Elevator.png"/>
</p>

## 📖 目录

[✏️ 心路历程](https://github.com/clos0710/Elevators#%EF%B8%8F-%E5%BF%83%E8%B7%AF%E5%8E%86%E7%A8%8B) | [💻 开发准备](https://github.com/clos0710/Elevators#-%E5%BC%80%E5%8F%91%E5%87%86%E5%A4%87) | [🎪 场景预设](https://github.com/clos0710/Elevators#-%E5%9C%BA%E6%99%AF%E9%A2%84%E8%AE%BE) | [🏢 场景分析](https://github.com/clos0710/Elevators#-%E5%9C%BA%E6%99%AF%E5%88%86%E6%9E%90) | [🤔 调度算法](https://github.com/clos0710/Elevators#-%E8%B0%83%E5%BA%A6%E7%AE%97%E6%B3%95) | [🚌 横向对比](https://github.com/clos0710/Elevators#-%E6%A8%AA%E5%90%91%E5%AF%B9%E6%AF%94) | [🐆 优化思路](https://github.com/clos0710/Elevators#-%E4%BC%98%E5%8C%96%E6%80%9D%E8%B7%AF) | [🥰 写在最后](https://github.com/clos0710/Elevators#-%E5%86%99%E5%9C%A8%E6%9C%80%E5%90%8E)

## ✏️ 心路历程

大一上学期有门课，叫计算机程序设计入门。课程结束前，老师布置了一道大作业：用C语言实现电梯调度。
 
接到题目我很兴奋。写了太多的Hello World，再难点也就是递归，终于有个和实际结合的复杂案例能检验一下学习成果了。
 
第一周洋洋洒洒写了几十行代码，电梯总不能按照实际体验的那样去响应。最可怕的是，电梯一旦运行停不下来，被同学嘲笑像倪匡科幻小说《电梯》中的情形。

随着思考的深入，问题也越来越多，例如如何在电梯运行的同时模拟乘客的到来与离去，多部电梯间如何协作。加上对C语言标准库的运用不熟悉，热情一天天在消退。

最终到了验收环节，无可奈何提交了一个半成品。但我发现，其他同学对我的问题也没有更好的解决方案。更幸运的是，老师并没有那么严格，半成品居然也顺利通过了验收。

**但自此，实现电梯调度模拟成为我的一块心病。**

读研的时候用Java实现过一次。如今已工作十多年，久不碰研发，但空闲之余再次引起了我的兴趣。这次我打算将代码公开到Github上并详细记录下实现的过程，帮助遇到同样问题的同学、听取高手们的建议、纪念逝去的青春。

[🚀 回到目录](https://github.com/clos0710/Elevators#-%E7%9B%AE%E5%BD%95)

## 💻 开发准备

**1. 用什么语言开发？**
 
答：Java。尽管十多年没碰过Java，但周围很多攻城狮，且项目大多数使用Java开发，遇到问题可以咨询的人比较多😆。
 
**2. Java什么版本？**
 
答：1.8.0_202。网上说这个版本免费，版本低也能避免一些兼容性问题。
 
**3. 用什么IDE？**
 
答：Eclipse免费版。最开始使用的是IntellJ Idea，后来想想还是尊重一下版权，换回了Eclipse。除了代码补全略卡外，其他都很丝滑（有一个编译器一致性问题，已解决）。
 
**4. 会使用图形界面形象展示乘客、电梯吗？**
 
答：不会。核心关注点是电梯调度算法，而不是图形化界面。换言之，图形界面做得再炫，电梯响应/运行不正常也是白搭。不过程序中还是简单用到了图形组件，主要用来打印乘客到达/离去、电梯运行/调度等日志信息。

[🚀 回到目录](https://github.com/clos0710/Elevators#-%E7%9B%AE%E5%BD%95)

## 🎪 场景预设

现实中有各式各样的电梯，需要对其场景进行明确：
 
**1. 电梯有几部？最高多少层？**
 
答：可配置。目前实现成了main函数中的常量。为了模拟多部电梯调度的复杂场景，建议电梯最少有2部，楼层不少于7层（《住宅设计规范》：7层楼(含)以上的房屋须装电梯）。
 
**2. 是否区分高低电梯？奇偶电梯？**
 
答：不区分。现实中有的电梯区分高低、奇偶，有的电梯某些楼层不停靠，另外还有专梯（只在某些楼层停靠的电梯）。为了简化，本程序未实现。
 
**3. 是否有地下车库？**
 
答：没有。同样是为了简化处理。地库的楼层编号一般从-1开始，依次减1，没有楼层编号为0层的情形。
 
**4. 是否有限高、限宽、限重？**
 
答：没有。限高、限宽在程序上可以对电梯进行特殊标记，有特殊需求的乘客必须乘坐特殊电梯，反过来，特殊电梯却不一定只服务特殊乘客。现实中宜家的楼里就有类似的超大电梯，用于大型家具出入。限重在程序上可以处理为限人数，到达额定人数，电梯不再载入新乘客。
 
**5. 是否有启停时间？**
 
答：没有。但现实中，无论是例行检查、故障维修、处理紧急事件，还是定期保养、节省电费，都可能定时不定时将某部电梯停用。已停用电梯程序上不再参与调度。
 
**6. 多部电梯是否共用同一套上下行按钮？**
 
答：是的。现实中多部电梯很常见，细心的话会发现每部电梯边上都有属于自己的上下按钮，本质上每部电梯独立控制；也存在多部电梯共享上下按钮，本质上属于共享控制。共享控制调度起来更复杂，但乘客体验更优。

**7. 乘客如何模拟？**
 
答：利用Java的随机函数不定时产生一名乘客。为了最大限度模拟真实情况，乘客上了电梯后电梯才会知道该乘客的目标楼层（而不是乘客产生时就有了目标楼层并告知电梯调度中心），同时假定乘客不会按错上行、下行按钮（比如乘客在4层，要去5层，不会按向下按钮）。

[🚀 回到目录](https://github.com/clos0710/Elevators#-%E7%9B%AE%E5%BD%95)

## 🏢 场景分析

指将场景中各种实物及关系捋清楚，对应到程序中的类、属性和方法。
 
电梯调度场景分为两部分：实物类包含电梯及乘客；动作类包含模拟乘客到达、进行电梯调度和模拟电梯运行。
 
**需要重点说明的是：这种分类不是一蹴而就，而是在编写代码的过程中不断的调整才最终定下来的，提前把所有情况所有问题都想明白是不可能的。而且，这种分类也不一定是最优的！**
 
- 电梯：编号、状态（闲置、上行、下行）、总楼层、当前楼层、上行请求、下行请求、步长（用作分析优化）
 
- 乘客：编号、状态（上行、下行）、当前楼层、目标楼层、是否等待、是否被服务、电梯编号（进入了哪部电梯）
 
- 乘客到达：最少x秒、最大y秒模拟一名乘客到达，乘客在m层，需要上行或下行。
 
- 电梯调度：乘客到达后，根据乘客需求和电梯的运行轨迹，计算得出到达乘客所在楼层路径最短且同向的电梯。
 
- 电梯运行：电梯每隔n秒上行或下行一层，并接、送乘客。
 
为了更好的模拟电梯调度的实际情况，程序使用到的基本技术有：
 
- 操作系统：多线程（含线程间通信），解决电梯运行同时乘客到达问题
 
- 数据结构：链表（插入、删除、排序），模拟多个乘客、多部电梯
 
- 图形编程：图形框架（JFrame）、图形面板（JPanel）、文本区（JTextArea），打印各类日志信息

[🚀 回到目录](https://github.com/clos0710/Elevators#-%E7%9B%AE%E5%BD%95)

## 🤔 调度算法

电梯调度模拟中最核心也是最复杂的就是**调度算法**了。网上有很多资料，本程序使用到的是所谓的look算法（即先向某方向运行至最远，再折返向另一方向运行至最远，如此往复），与机械硬盘磁头寻道算法类似。
 
将乘客数量分为1名、2名、3\~N名，电梯数量分为1部、2部、3\~N部，从易到难、逐步分析：

[1名乘客、1部电梯](https://github.com/clos0710/Elevators#--1%E5%90%8D%E4%B9%98%E5%AE%A21%E9%83%A8%E7%94%B5%E6%A2%AF) | [1名乘客、2\~N部电梯](https://github.com/clos0710/Elevators#--1%E5%90%8D%E4%B9%98%E5%AE%A22n%E9%83%A8%E7%94%B5%E6%A2%AF)

[2名乘客、1部电梯](https://github.com/clos0710/Elevators#--2%E5%90%8D%E4%B9%98%E5%AE%A21%E9%83%A8%E7%94%B5%E6%A2%AF) | [2名乘客、2部电梯](https://github.com/clos0710/Elevators#--2%E5%90%8D%E4%B9%98%E5%AE%A22%E9%83%A8%E7%94%B5%E6%A2%AF) | [2名乘客、3\~N部电梯](https://github.com/clos0710/Elevators#--2%E5%90%8D%E4%B9%98%E5%AE%A23n%E9%83%A8%E7%94%B5%E6%A2%AF)

[3\~N名乘客、1部电梯](https://github.com/clos0710/Elevators#--3n%E5%90%8D%E4%B9%98%E5%AE%A21%E9%83%A8%E7%94%B5%E6%A2%AF) | [3\~N名乘客、2部电梯](https://github.com/clos0710/Elevators#--3n%E5%90%8D%E4%B9%98%E5%AE%A22%E9%83%A8%E7%94%B5%E6%A2%AF) | [3\~N名乘客、3\~N部电梯](https://github.com/clos0710/Elevators#--3n%E5%90%8D%E4%B9%98%E5%AE%A23n%E9%83%A8%E7%94%B5%E6%A2%AF)
 
### - 1名乘客、1部电梯
   
  - 初始状态：乘客到达x层，按上行或下行按钮，电梯闲置在y层。
     
  - 如何响应：
       
    1. 如x==y，乘客进入电梯，电梯获取乘客目标楼层，并上行或下行至目标楼层，乘客离开电梯，该乘客服务完毕，电梯闲置在目标楼层；
       
    2. 如xy，电梯上行至x层，后续处理与x==y时一致；
       
    3. 如x<y，电梯下行至x层，后续处理与x==y时一致。

### - 1名乘客、2\~N部电梯
   
  - 初始状态：乘客到达x层，按上行或下行按钮，电梯1闲置在y层...电梯N闲置在z层。
   
  - 如何响应：
     
    这个案例与1名乘客、1部电梯相比，最大的区别就是需要判断哪部电梯来响应乘客。**本程序调度中心使用的是“距离优先”的逻辑，即运行到乘客所在楼层距离最短的电梯来响应该乘客**。
     
    1. 计算|y-x|和|z-x|哪个更小（做减法需要取绝对值），即哪个电梯离乘客更近；
     
    2. 由距离更近的电梯向乘客楼层运行（距离相等，电梯编号靠前的响应），其他响应与1名乘客、1部电梯的情形一样。

### - 2名乘客、1部电梯
   
  - 初始状态：乘客1到达x层，乘客2到达y层，乘客1、乘客2按上行或下行按钮（可不相同），电梯闲置在z层。
   
  - 如何响应：
     
    此案例比较复杂。电梯根据乘客按下按钮的时间先后顺序来判断先到x层还是先到y层。
     
    1. 乘客1先到，电梯从z层运行至x层服务乘客1；
     
    2. 当乘客1和乘客2同向（同为上行或下行），x=y且y=z（同为上行时）或x<=y且y<=z（同为下行时），此时电梯可“顺路”接上乘客2，而不是直接运行至x层；
     
    3. 其他情形（即不顺路），电梯需先运行至x层，接上乘客1，再响应乘客1或乘客2；
     
    4. 乘客2先到的情况类似。
        
    **❓ 站在电梯角度，该如何处理这种复杂情形呢？**
        
    答：电梯可看作根据乘客的请求，在上行和下行之间“折返”运动（如下图）。
        
    ![](https://github.com/clos0710/Elevators/blob/master/img/2passengers1elevator.png)
        
    即电梯向某个方向运行，直到能够到达的最远距离，再折返向另一个方向运行，直到能够到达的最远距离，如此往复直到没有任何乘客请求。
        
    **电梯维护了两个“列表”：上行列表和下行列表**。每当乘客到达并按下按钮，或者乘客进入电梯并按下目标楼层后，电梯更新上行或下行列表：
     
    1. 当乘客到达并按上行按钮、或上行乘客进入电梯并按目标楼层时，将乘客到达楼层或目标楼层加入上行列表，对列表进行从小到大排序；
     
    2. 当乘客到达并按下行按钮、或下行乘客进入电梯并按目标楼层时，将乘客到达楼层或目标楼层加入下行列表，对列表进行从大到小排序。
        
    电梯上行或下行，直到响应了上行或下行列表的最后一个请求，再根据反向列表决定是否需要同向或反向运行或闲置。
        
    **本案例中，假定乘客1先到、上行，乘客2后到、上行，则将x、y加入电梯的上行列表，并进行从小到大的排序。电梯根据该列表以及电梯所在楼层，先运行至最近的楼层（可能是x，也可能是y）。接上乘客后获取目标楼层，加入上行列表，并从上行列表中去除本楼层，再运行至最近的楼层。如此反复直至所有请求都已响应。**
 
### - 2名乘客、2部电梯
   
  - 初始状态：乘客1到达x层，乘客2到达y层，乘客1、乘客2按上行或下行按钮（可不相同），电梯1闲置在m层，电梯2闲置在n层。
   
  - 如何响应：
     
    这个案例让电梯调度变得更加复杂。
     
    前面说过，本程序的场景预设中，电梯之间是共享一套上下行按钮的（即每个楼层只有一个上行按钮和一个下行按钮）。假定乘客1先到、乘客2后到，乘客1由电梯1响应。电梯1开始向乘客1所在楼层运行。当乘客2到达时，调度中心需要判断运行中的电梯1和闲置的电梯2哪个到乘客2的所在楼层距离最短。
     
    1. 乘客1到达时，和1名乘客、2部电梯的响应方式相同；
     
    2. 乘客2到达时，电梯1已经运行到了s层，要到x层接上乘客1。电梯1与乘客2的距离**至少**为|x-s|+|x-y|（“至少”是因为乘客1的目标楼层此刻还未知）。电梯2与乘客2的距离为|m-y|。
     
    3. 比较电梯1和电梯2，更近的电梯将乘客2的目标楼层加入上行或下行列表，并进行排序，由该电梯响应。
        
    电梯1与乘客2的距离参考下图：
        
    ![](https://github.com/clos0710/Elevators/blob/master/img/2passengers2elevators.png)
        
    本案例中，部分情况下调度中心给出的决策（即哪个电梯距离乘客最近）是**当前最优解**，而不是**实际最优解**，例如下图：
        
    ![](https://github.com/clos0710/Elevators/blob/master/img/2passengers2elevators(CurrentSolution).png)
        
    图中，乘客2到达时，电梯1需要先接上乘客1，接上乘客1前电梯1并不知道乘客1的目标楼层。这种情况下，调度中心判断电梯1到达乘客2所需要经过的楼层数为5，电梯2到达乘客2所需要经过的楼层数为7。电梯1距离乘客2**更近（当前最优解）**。
        
    **但实际情况是，电梯1接上乘客1后，要继续上行至乘客1的目标楼层，再折返接上乘客2，此时电梯1到达乘客2所需要经过的楼层数为9。相比电梯2到达乘客2的楼层数7要多，电梯2距离乘客2更近（实际最优解）。**
        
    **当然我们也可以举出类似的“反例”。这也是实际电梯调度中的常见问题。**
 
### - 2名乘客、3\~N部电梯
   
  - 和2部电梯情况一样。逐一计算每部电梯到达后来乘客的距离，距离最近的响应该乘客请求。如果多部电梯距离相同，则编号更小的响应。
 
### - 3\~N名乘客、1部电梯
   
  - 和2名乘客、1部电梯的情况一样。无论多少名乘客，每个楼层上行、下行按钮只有两个，新乘客到来，如果当前楼层的上行、下行按钮均已按下，就不会再按（再按也没有效果）。另外，无论不同楼层的乘客如何按下按钮，理论上都能够分出时间先后（即同时按下按钮几乎是个伪命题），哪怕时间仅相差几毫秒。    
 
### - 3\~N名乘客、2部电梯
   
  - 这个案例的复杂之处在于要计算每个电梯的**当前最优解**。电梯始终是在上行和下行之间往复运动，因此计算电梯当前最优解的方法是：
     
    1. 当电梯上行、乘客上行，且乘客楼层比电梯楼层大时，最优解是电梯和乘客之间的距离；
     
    2. 当电梯上行、乘客上行，且乘客楼层比电梯楼层小时，最优解是电梯和最高层之间的距离，加上最高层和最低层的距离，加上最低层和乘客的距离（电梯上行至最高、再下行至最低、再上行接上乘客）；
     
    3. 当电梯上行、乘客下行，且乘客楼层比电梯上行需到达的最高楼层大时，最优解是电梯和乘客之间的距离；
     
    4. 当电梯上行、乘客下行，且乘客楼层比电梯上行需到达的最高楼层小时，最优解是电梯和最高层之间的距离，加上最高层和乘客之间的距离（电梯上行至最高，在下行接上乘客）；
     
    5. 当电梯下行时的情况和1\~4类似；
     
    6. 当电梯闲置时，最优解是电梯和乘客之间的距离。
   
  - 比较每一部电梯的当前最优解，取最近的电梯响应乘客。

  - 其中第二条比较难以理解，可以参考下图：

    ![](https://github.com/clos0710/Elevators/blob/master/img/3passengers1elevator.png)

	**电梯要接上乘客3，需要绕个大圈子：接上乘客1，送走乘客1，折返接上乘客2，送走乘客2，最后折返接上乘客3。**

### - 3\~N名乘客、3\~N部电梯
   
  - 和3\~N名乘客、2部电梯情况一样，不再赘述。
 
至此，电梯调度算法分析完毕。**👍 如果你能仔细看完这部分并且理解其中的含义，必须点个大大的赞！**

[🚀 回到目录](https://github.com/clos0710/Elevators#-%E7%9B%AE%E5%BD%95)

## 🚌 横向对比

**1. 与公共交通（巴士、地铁）调度的区别？**
 
|       | 是否知道该站（层）有乘客？ | 是否知道已服务乘客到达哪些站（层）？ | 是否每站（层）都停靠？ | 是否可中途闲置？ |
|:-----:|:----------------------: |:--------------------------------:|:-------------------:|:--------------:|
|电梯   | 是                       | 是                               | 否                  | 可以            |
|公交   | 否                       | 否                               | 是                  | 不可以          |
 
**2. 与火车调度的区别？**
 
|       | 是否知道该站（层）有乘客？ | 是否知道已服务乘客到达哪些站（层）？ | 是否每站（层）都停靠？ | 是否可中途闲置？ |
|:-----:|:----------------------: |:--------------------------------:|:-------------------:|:--------------:|
|电梯   | 是                       | 是                               | 否                  | 可以            |
|火车   | 是                       | 是                               | 是                  | 不可以          |
 
有一个细节：火车开车前，所有乘客的是上车站、下车站都已经基本确定（购买了火车车票），电梯只知道乘客的所在楼层，只有接上乘客才知道目标楼层。
 
总体上，电梯调度和公交、火车调度有很多相似之处，但也有不同。之所以和这两者比较，一方面为了加深对电梯调度的理解，另一方面想强调做程序开发视角要开阔些。对调度程序略加改动可以模拟出公交、火车的场景，甚至可以通过调整参数，模拟中国春运。

[🚀 回到目录](https://github.com/clos0710/Elevators#-%E7%9B%AE%E5%BD%95)

## 🐆 优化思路

本电梯的调度算法已经可以满足一些简单场景的乘坐需求，但依然存在很大优化空间。例如下面几种情况（参考抖音视频：https://v.douyin.com/iej3NpYN/ ）：
 
- **大型写字楼**
 
其特点是早晚高峰乘客密集，早高峰最大的需求是从1层上行，晚高峰最大的需求是下行至1层。特别是早高峰，需要尽快运送完所有乘客，否则大家就会因为电梯的调度问题而迟到（甚至是扣工资，进而影响一天、一周的心情😂）。 
 
- **大型酒店**
 
其特点是乘坐需求分散、不存在明显高峰，但流向复杂。这时候电梯调度需要关注的重点是每个乘客的等候时间（从按下电梯按钮到进入电梯的时间）、所有乘客的平均等候时间。如果酒店客人等电梯的时间过长会对酒店的服务产生怀疑甚至是抱怨（要是因为这个原因赶不上火车或飞机麻烦就更大了）。
 
- **超高综合楼**
 
例如迪拜塔，低层酒店、中层住宅、高层观光，还有写字楼的功能。这就是一个需求综合体，其电梯调度更加复杂，要更多的指标来衡量，甚至需要使用人工智能动态调整。
 
实际情况中，需要结合预估的乘客人数、乘客的需求特点、楼层用途、楼层高度、电梯载重等因素，来设计电梯的数量、运行的速度、电梯的分布等，并通过电梯调度模拟软件来得到理论最优解、次优解。不过另外要考虑的是，越多的电梯、越快的电梯占地越多、造价越高，公摊越大、成本越大。
 
**综合来说，电梯调度的主要指标有：**
 
1. 5分钟载客率（大型写字楼）。主要考察电梯群能否快速运送乘客。例如运送到达1层的300名乘客，10分钟内全部服务完成算一次成功，连续模拟100次，计算成功率。
 
2. 平均等候时间（大型酒店）。主要考察电梯群能否合理响应乘客。例如300名乘客，楼层随机、目标随机，每一位乘客等候时间加总后除以300，计算平均时间。
 
3. 轿厢利用率。主要考察能否合理利用每部电梯。例如每次模拟每部电梯运送乘客的数量是否大致相当。
 
4. 平均运动时间。同样考察能否合理利用每部电梯。例如每次模拟每部电梯运行步长是否大致相当。
 
除此之外，还可结合实际情况提出更多更复杂的考察指标，只要合理自洽都行（比如电梯初始时是否都要停靠在1层，如果不是，停靠在几层能最高效服务乘客），再根据指标和预设的合格线来优化调度程序。

[🚀 回到目录](https://github.com/clos0710/Elevators#-%E7%9B%AE%E5%BD%95)

## 🥰 写在最后

人的一生很长、人的一生也很短。一生中做一些让自己印象深刻的事情并不容易，大部分还是在平淡中度过。
 
当回首往事，无论是学习、工作、爱情，能从容面对，不后悔、不焦虑、不悲伤，也许就是对自己最好的交代。
 
朋友，如果你也有未了之事，就立刻开展行动吧，心有所向，行有所达，给自己一个最好的交代！

[🚀 回到目录](https://github.com/clos0710/Elevators#-%E7%9B%AE%E5%BD%95)