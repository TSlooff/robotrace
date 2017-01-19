varying vec3 pos;

float height(float x, float y) {
    float z = 0.6*sin(0.5*x + 0.3*y) + 0.4 * cos(x - 0.5*y);
    return z;
}

void main() {
    float x = gl_Vertex.x;
    float y = gl_Vertex.y;
    float z = height(x,y);
    
    pos = vec3(x,y,z);
    gl_Position = gl_ModelViewProjectionMatrix * vec4(x,y,z,1);
}
