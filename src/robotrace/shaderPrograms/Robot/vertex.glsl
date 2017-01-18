#version 120
varying vec3 N;
varying vec3 P;

void main()
{
	vec4 P4 = (gl_ModelViewMatrix*gl_Vertex);
	P = P4.xyz/P4.w;
	N = normalize(gl_NormalMatrix*gl_Normal);
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;

}   