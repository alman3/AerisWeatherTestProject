# AerisWeatherTestProject
It has been around 7 years since I really used Android Studio in any serious way (
and even then, I wasn't an expert by any means).
I've never used the Kotlin language nor the MapBox and AerisWeather APIs before, and I have not
used many web-like interfaces before, either.
It also didn't help that I was sick during much of this time.

Within the time I spent on this assignment, I was not able to get the all of code to the point
that it worked properly, but I was able to get some functionality, and I was able
to include code snippets for some of the basic functionality I think is needed (but can't
verify the ideas actually work).

I spent a lot of time trying to get dependencies, imports,
and gradle implementations to work. It also took me a while to learn that some settings
disallow the use of other settings (such as the fact that databinding and view binding
cannot be used at the same time). I have very limited experience with Android and
modern Java / Kotlin style includes and dependencies, so much of my time was spent just
working through that.

I was able to get the unlock codes to work for MapBox, and got a
basic map displaying for MapBox. The MapBox site provided an example for heatmaps,
and I got a variation of the example to compile, but the heatmap itself doesn't appear.
I modified this example to what I *think* should mostly rework it to use AerisWeather data, but
again, I can't test it since the heatmap doesn't appear to begin with, so it probably isn't quite
right.
--------------------------------------

Design decisions:

I chose Mapbox over GoogleMaps because of the heatmaps, and because I wanted to use something more different than I used before (having used the Google Ads API before).

I used Kotlin instead of Java, because I never used it before, and was curious. Its also been
long enough since I used Java, that newer versions would almost be like a using new language for
me anyway.

I started with one of the standard Android Studio templates
--------------------------------------

Examples that I referenced:

https://docs.mapbox.com/android/maps/guides/install/

https://docs.mapbox.com/android/maps/examples/heatmap-layer/

https://www.section.io/engineering-education/how-to-use-databinding-in-android-using-kotlin/

https://www.section.io/engineering-education/implementing-mvvm-architecture-in-android-using-kotlin/
