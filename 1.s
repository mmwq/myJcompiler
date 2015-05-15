.386
.MODEL FLAT
ExitProcess PROTO NEAR32 stdcall, dwExitCode:DWORD
INCLUDE io.h            ; header file for input/output
cr      EQU     0dh     ; carriage return character
Lf      EQU     0ah     ; line feed
.STACK  4096            ; reserve 4096-byte stack
.DATA                   ; reserve storage for data
dataseg       BYTE   21 DUP (?)
.CODE                           ; start of main program code
_start:
	mov eax,
	add eax,1
	mov dataseg+13,eax
	mov eax,dataseg+
	add eax,13
	mov dataseg+0,eax
	mov eax,
	add eax,2
	mov dataseg+13,eax
	mov eax,dataseg+
	mov eax,dataseg+13
	imul ebx
	mov dataseg+17,eax
	mov eax,dataseg+
	add eax,17
	mov dataseg+0,eax
	mov eax,
	add eax,1
	mov dataseg+13,eax
	mov eax,dataseg+
	add eax,dataseg+13
	mov dataseg+17,eax
	mov eax,dataseg+
	add eax,17
	mov dataseg+0,eax
	mov eax,
	add eax,1
	mov dataseg+13,eax
	mov eax,dataseg+
	add eax,dataseg+13
	mov dataseg+17,eax
	mov eax,dataseg+
	add eax,17
	mov dataseg+8,eax
	INVOKE  ExitProcess, 0  ; exit with return code 0
PUBLIC _start 
END 