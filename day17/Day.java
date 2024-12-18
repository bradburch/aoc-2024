package day17;

import java.util.Arrays;
import java.util.Scanner;

public class Day {

    private Long registerA;
    private Long registerB;
    private Long registerC;
    private int[] program;

    private int instructionPointer = 0;
    private boolean jump = false;
    private StringBuffer outs = new StringBuffer();

    public void part1() {

        while (instructionPointer < program.length) {
            int opcode = program[instructionPointer];
            int operand = program[instructionPointer+1];
            instructions(opcode, operand);
            
            if (!jump) {
                instructionPointer += 2;
            }
            jump = false;
        }
        // Copied string without the last comma :) 
        System.out.println("Outs: " + outs.toString());
    }

    public void part2() {

    }

    private void instructions(int opCode, long operand) {
        switch (opCode) {
            case 0:
                adv(operand);
                break;
            case 1: 
                bxl(operand);
                break;
            case 2: 
                bst(operand);
                break;
            case 3: 
                jnz(operand);
                break;
            case 4:
                bxc(operand);
                break;
            case 5: 
                out(operand);
                break;
            case 6: 
                bdv(operand);
                break;
            case 7: 
                cdv(operand);
                break;
            default:
                break;
        }
    }

    private void adv(Long operand) {
        registerA = divide(operand);
    }

    private void bxl(Long operand) {
        registerB = registerB ^ operand;
    }

    private void bst(Long operand) {
        Long combo = getOperand(operand);
        registerB = combo % 8;
    }

    private void jnz(Long operand) {
        if (registerA != 0L){
            instructionPointer = Math.toIntExact(operand);
            jump = true;
        }
    }

    private void bxc(Long operand) {
        registerB = registerB ^ registerC;
    }

    private void out(Long operand) {
        Long combo = getOperand(operand);
        Long result = combo % 8;
        outs.append(result + ",");
    }

    private void bdv(Long operand) {
        registerB = divide(operand);
    }

    private void cdv(Long operand) {
        registerC = divide(operand);
    }

    private Long divide(Long operand) {
        Long combo = getOperand(operand);
        return (long) (registerA / Math.pow(2, combo));
    }

    private Long getOperand(long operand) {
        if (operand <= 3L) return (long) operand;
        else if (operand == 4L) return Long.valueOf(registerA);
        else if (operand == 5L) return Long.valueOf(registerB);
        else if (operand == 6L) return Long.valueOf(registerC);
        else {
            System.out.println("operand: " + operand);
            return null;
        }
    }

    public void parseInput(String data) {
        String[] registerProgram = data.split("\n\n");
        populateRegisters(registerProgram[0]);
        populateProgram(registerProgram[1]);
    }

    private void populateRegisters(String data) {
        Scanner scanner = new Scanner(data);

        String line = scanner.nextLine();
        String[] nums = line.split(": ");

        registerA = Long.valueOf(nums[1]);

        line = scanner.nextLine();
        nums = line.split(": ");

        registerB = Long.valueOf(nums[1]);

        line = scanner.nextLine();
        nums = line.split(": ");

        registerC = Long.valueOf(nums[1]);

        scanner.close();
    }

    private void populateProgram(String data) {
        String commas = data.split(": ")[1].trim();

        program = Arrays.stream(commas.split(",")).mapToInt(Integer::parseInt).toArray();
    }
    
}
