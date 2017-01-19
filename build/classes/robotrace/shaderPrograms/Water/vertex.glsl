// simple vertex shader
uniform float tAnim;

float height(float x, float y) {
    float z = 0.3* sin(15*x + tAnim * 2.0)+ 0.2* sin(67*x + tAnim * 1.4);
    return z;
}

void main() {
    float x = gl_Vertex.x;
    float y = gl_Vertex.y;
    float z = height(x,y);
    
    gl_Position = gl_ModelViewProjectionMatrix * vec4(x,y,z,1);
    gl_FrontColor = gl_Color;
}
