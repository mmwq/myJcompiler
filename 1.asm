DATAS SEGMENT
dataseg       BYTE   24 DUP (?)
X DW 10000,1000,100,10,1
DATAS ENDS
CODES SEGMENT
	ASSUME CS:CODES,DS:DATAS
print: 
	MOV SI,offset X
	xor dx,dx
	mov bx,ax
	MOV CX,5
l1:div word ptr [SI]
	push dx
	CMP CX,1
JZ l2
	cmp dx,bx
	jz skip
	l2:    mov dl,al
	OR DL,30H
	mov ah,02h
	int 21h
skip:  pop ax
	xor dx,dx
	add SI,2
	LOOP l1 
	ret
START:
	MOV AX,DATAS
	MOV DS,AX
		mov ax,10
		add ax,0
		mov bx,12
		mov [bx],ax
		mov bx,12
		mov ax,[bx]
		add ax,0
		mov bx,0
		mov [bx],ax
		mov ax,10
		add ax,0
		mov bx,12
		mov [bx],ax
		mov bx,12
		mov ax,[bx]
		mov bx,0
		add ax,[bx]
		mov bx,16
		mov [bx],ax
		mov ax,2
		add ax,0
		mov bx,12
		mov [bx],ax
		mov bx,16
		mov ax,[bx]
		mov bx,12
		div  byte ptr DS:[bx]
		mov bx,20
		mov [bx],ax
		mov bx,20
		mov ax,[bx]
		add ax,0
		mov bx,4
		mov [bx],ax
		mov ax,5
		add ax,0
		mov bx,12
		mov [bx],ax
		mov bx,12
		mov ax,[bx]
		mov bx,4
		add ax,[bx]
		mov bx,16
		mov [bx],ax
		mov bx,16
		mov ax,[bx]
		add ax,0
		mov bx,8
		mov [bx],ax
		mov ax,11
		add ax,0
		mov bx,12
		mov [bx],ax
		mov bx,8
		mov ax,[bx]
		mov bx,12
		cmp ax,[bx]
		jle bn1
		mov bx,16
		mov ax,0FFFFH
		mov [bx],ax
		jmp bnext1
	bn1:
		mov bx,16
		mov ax,0
		mov [bx],ax
	bnext1:
		mov bx,16
		mov ax,[bx]
		mov bx,0FFFFH
		cmp ax,bx
		jne no1
		mov ax,14
		add ax,0
		mov bx,12
		mov [bx],ax
		mov bx,8
		mov ax,[bx]
		mov bx,12
		cmp ax,[bx]
		jge bn2
		mov bx,16
		mov ax,0FFFFH
		mov [bx],ax
		jmp bnext2
	bn2:
		mov bx,16
		mov ax,0
		mov [bx],ax
	bnext2:
		mov bx,16
		mov ax,[bx]
		mov bx,0FFFFH
		cmp ax,bx
		jne no2
		mov ax,1
		add ax,0
		mov bx,12
		mov [bx],ax
		mov bx,12
		mov ax,[bx]
		mov bx,8
		add ax,[bx]
		mov bx,16
		mov [bx],ax
		mov bx,16
		mov ax,[bx]
		add ax,0
		mov bx,8
		mov [bx],ax
		jmp next2
	no2:
		mov ax,1
		add ax,0
		mov bx,12
		mov [bx],ax
		mov bx,8
		mov ax,[bx]
		mov bx,12
		sub ax,[bx]
		mov bx,16
		mov [bx],ax
		mov bx,16
		mov ax,[bx]
		add ax,0
		mov bx,8
		mov [bx],ax
	next2:
	next1:
	no1:
		mov bx,8
		mov ax,[bx]
	call print
	MOV AH,4CH
	INT 21H
CODES ENDS
END START