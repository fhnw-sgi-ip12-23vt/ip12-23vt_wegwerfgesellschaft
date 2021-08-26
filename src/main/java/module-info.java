/**
 * Use "open module" here so FXGL can access the images in resources.
 * For more info see https://github.com/AlmasB/FXGL/wiki/FXGL-11-Migration-Guide#modularity
 */
open module com.pi4j.fxgl {
    // Module Exports

    // Pi4J Modules
    requires com.pi4j;
    requires com.pi4j.library.pigpio;
    requires com.pi4j.plugin.pigpio;
    requires com.pi4j.plugin.raspberrypi;
    requires com.pi4j.plugin.mock;
    uses com.pi4j.extension.Extension;
    uses com.pi4j.provider.Provider;

    // JavaFX
    requires javafx.controls;

    // FXGL
    requires com.almasb.fxgl.all;

    // allow access to classes in the following namespaces for Pi4J annotation processing
    //exports com.pi4j.fxgl to com.almasb.fxgl.core;
}
