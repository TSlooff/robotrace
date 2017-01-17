#version 120
varying vec3 N;
varying vec3 P;

vec4 shading(vec3 P, vec3 N, gl_LightSourceParameters light, gl_MaterialParameters mat) {
    vec4 result  = vec4(0,0,0,1);		
    
    vec3 L = normalize((light.position.xyz/light.position.w)-P);
    result += mat.diffuse*light.diffuse*max(dot(N,L),0.0);
    
    vec3 E = normalize(-P);
    vec3 V = 2.0*dot(N,L)*N-L;
    result += mat.specular*light.specular*pow(max(dot(V,E), 0.0), mat.shininess);
    
    return result;
}

void main() {
    
    gl_LightSourceParameters light = gl_LightSource[0];
    gl_MaterialParameters mat = gl_FrontMaterial;
    gl_FragColor = shading(P,N,light,mat);
    
}