varying vec3 pos;

void main()
{   
    if (pos.z < -0.6) {
        gl_FragColor = vec4(0.0, 0.02745098039, 0.14901960784, 1); 
    } else if (pos.z < 0.0) {
        gl_FragColor = vec4(0.07058823529, 0.06666666666, 0.3294117647, 1); 
    } else if (pos.z < 0.5) {      
        gl_FragColor = vec4(0.82352941176, 0.85882352941, 0.60784313725, 1);
    } else {
        gl_FragColor = vec4(0.1725490196, 0.55686274509, 0.2862745098, 1);
    }
}
