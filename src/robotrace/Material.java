package robotrace;

/**
* Materials that can be used for the robots.
*/
public enum Material {

    /** 
     * Gold material properties.
     * Modify the default values to make it look like gold.
     */
    GOLD (
        new float[] {1f, 0.96f, 0.33f, 1f},
        new float[] {1f, 0.67f, 0.0f, 1f},
        new float[] {1f, 1f, 1f, 1f},
        28f
    ),

    /**
     * Silver material properties.
     * Modify the default values to make it look like silver.
     */
    SILVER (
        new float[] {0.75294117647f, 0.75294117647f, 0.75294117647f, 1f},
        new float[] {0.75294117647f, 0.75294117647f, 0.75294117647f, 1f},
        new float[] {0.508273f, 0.508273f, 0.508273f, 1f},
        27f),

    /** 
     * Wood material properties.
     * Modify the default values to make it look like wood.
     */
    WOOD (
        new float[] {0.10980392156f, 0.32549019607f, 0.10980392156f, 1f},
        new float[] {0.45490196078f, 0.32549019607f, 0.10980392156f, 1f},
        new float[] {0.0f, 0.2f, 0.0f, 1f},
        50f),

    /**
     * Orange material properties.
     * Modify the default values to make it look like orange.
     */
    ORANGE (
        new float[] {1.0f, 0.64705882352f, 0.0f, 1f},
        new float[] {1.0f, 0.54901960784f, 0.0f, 1f},
        new float[] {1.0f, 1f, 1f, 1f},
        32f);

    float[] ambient;

    /** The diffuse RGBA reflectance of the material. */
    float[] diffuse;

    /** The specular RGBA reflectance of the material. */
    float[] specular;
    
    /** The specular exponent of the material. */
    float shininess;

    /**
     * Constructs a new material with diffuse and specular properties.
     */
    private Material(float[] ambient,float[] diffuse, float[] specular, float shininess) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }
}