import numpy as np
import transformer
import dsp

#signals = np.load('signalsnew145-one.npy')
#labels = np.load('labelsnew145-one.npy')
#

input_nc=3;
def haha():
    print("haha")
#def Preprocessing(signals):
def Preprocessing(ssPath):
    signals=np.loadtxt(ssPath)
    #print(type(signals))
    #print(signals[0][0])
    #print(signals.shape)
    #return
    #signals=np.array(signals)
    signals=signals.reshape(1,3,15000)
    data = signals.astype(dtype='float64')

    print(signals.shape)
    #return
    shape = data.shape
    #print(shape)

    #return
    data_calculate = data.copy();
    #return
    data_sort = np.sort(data_calculate, axis=None)
    th5 = data_sort[int(0.05 * len(data_sort))];
    th95 = data_sort[int(0.95 * len(data_sort))];
    #return
    baseline = (th5 + th95) / 2;
    sigma = (th95 - th5) / 2;
    if sigma == 0:
        sigma = 1e-06;
    result = (data - baseline) / sigma; #5-95归一化
    data = result[0]
    #print(data)#data和signal可能类型有区别一个是ndarray一个是list
    result = []
    h,w=257,140;
    #return
    for i in range(1):
        for j in range(input_nc):
            #print(data[j])
            #spectrum = dsp.signal2spectrum(data[j], 512, 108, 1, 'store_true')
            spectrum = dsp.signal2spectrum(data[j], 512, 108)
            #print(spectrum.shape)
            spectrum = transformer.random_transform_2d(spectrum, (h, int(w * 0.9)), test_flag=True)
            result.append(spectrum)
    #result = (np.array(result)).reshape(1, input_nc, h, int(w * 0.9))
    result = (np.array(result)).reshape(1*input_nc* h* int(w * 0.9))
    #result = (np.array(result)).reshape(-1)

    signals=result
    return signals