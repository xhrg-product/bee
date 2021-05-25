### netty

netty的线程池准确的来说有三个。这三个全是和netty的api直接有关的。

```
serverBootstrap.group(boss, selector)
pipe.addLast(worker, new BizHandler());
```

以上代码中boss表示创建新链接的线程池，selector是执行io监听事件的线程池，worker对应他后面的Handler的执行的线程池。