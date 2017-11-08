package com.gungoren.gauss;

import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.String.format;

public class GaussGenerator {

    private float[] generateSeparableGaussKernel(double sigma, int kernelSize )
    {
        if( (kernelSize % 2) != 1 )
        {
            return new float[kernelSize];
        }

        int halfKernelSize = kernelSize/2;

        float[] kernel = new float[kernelSize];

        double cPI= 3.14159265358979323846;
        double mean     = halfKernelSize;
        double sum      = 0.0;
        for (int x = 0; x < kernelSize; ++x)
        {
            kernel[x] = (float)sqrt( exp( -0.5 * (pow((x-mean)/sigma, 2.0) + pow((mean)/sigma,2.0)) )
                    / (2 * cPI * sigma * sigma) );
            sum += kernel[x];
        }
        for (int x = 0; x < kernelSize; ++x)
            kernel[x] /= (float)sum;

        return kernel;
    }

    private float[] getAppropriateSeparableGauss( int kernelSize )
    {
        if( (kernelSize % 2) != 1 )
        {
            assert( false ); // kernel size must be odd number
            return new float[kernelSize];
        }

        // Search for sigma to cover the whole kernel size with sensible values (might not be ideal for all cases quality-wise but is good enough for performance testing)
        double epsilon = 2e-2f / kernelSize;
        double searchStep = 1.0;
        double sigma = 1.0;
        while( true )
        {
            float[] kernelAttempt = generateSeparableGaussKernel( sigma, kernelSize );
            if( kernelAttempt[0] > epsilon )
            {
                if( searchStep > 0.02 )
                {
                    sigma -= searchStep;
                    searchStep *= 0.1;
                    sigma += searchStep;
                    continue;
                }
                float[] retVal = new float[kernelSize];
                for (int i = 0; i < kernelSize; i++)
                    retVal[i] = kernelAttempt[i];
                return retVal;
            }

            sigma += searchStep;

            if( sigma > 1000.0 )
            {
                return new float[kernelSize];
            }
        }
    }

    private String generateGaussShaderKernelWeightsAndOffsets( int kernelSize)
    {
        // Gauss filter kernel & offset creation
        float[] inputKernel = getAppropriateSeparableGauss(kernelSize);

        float[] oneSideInputs = new float[kernelSize/2 + 1];
        for( int i = (kernelSize/2); i >= 0; i-- )
        {
            if( i == (kernelSize/2) )
                oneSideInputs[(kernelSize/2) - i] = inputKernel[i] * 0.5f;
            else
                oneSideInputs[(kernelSize/2) - i] = inputKernel[i];
        }

        int numSamples = oneSideInputs.length/2;

        float[] weights = new float[numSamples];

        for( int i = 0; i < numSamples; i++ )
        {
            float sum = oneSideInputs[i*2+0] + oneSideInputs[i*2+1];
            weights[i] = sum;
        }

        float[] offsets = new float[numSamples];

        for( int i = 0; i < numSamples; i++ )
        {
            offsets[i]= i*2.0f + oneSideInputs[i*2+1] / weights[i];
        }

        String indent = "    ";

        String shaderCode = ("");
        String eol = "\n";
        shaderCode += indent + "//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////;" + eol;
        shaderCode += indent + format( "// Kernel width %d x %d", kernelSize, kernelSize ) + eol;
        shaderCode += indent + format( "const int stepCount = %d;", numSamples ) + eol;

        shaderCode += indent + "const float gWeights[stepCount] ={" + eol;
        for( int i = 0; i < numSamples; i++ )
            shaderCode += indent + format( "   %.5f", weights[i] ) + ((i!=(numSamples-1))?(","):("")) + eol;
        shaderCode += indent + "};"+eol;
        shaderCode += indent + "const float gOffsets[stepCount] ={"+eol;
        for( int i = 0; i < numSamples; i++ )
            shaderCode += indent + format( "   %.5f", offsets[i] ) + ((i!=(numSamples-1))?(","):("")) + eol;
        shaderCode += indent + "};" + eol;
        return shaderCode;
    }

    public static void main(String[] args) {
        GaussGenerator generator = new GaussGenerator();
        for (int i = 3; i < 45; i = i + 2) {
            String resp = generator.generateGaussShaderKernelWeightsAndOffsets(i, false, false);
            System.out.println(resp);
        }
    }
}
