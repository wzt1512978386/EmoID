import scipy.signal
import scipy.fftpack as fftpack
import numpy as np

def signal2spectrum(data,stft_window_size,stft_stride, log_alpha = 0.1):
    zxx = scipy.signal.stft(data, window='hann', nperseg=stft_window_size,noverlap=stft_window_size-stft_stride)[2]
    spectrum = np.abs(zxx)

    spectrum = np.log1p(spectrum)
    h = spectrum.shape[0]
    x = np.linspace(h*log_alpha, h-1,num=h+1,dtype=np.int64)
    index = (np.log1p(x)-np.log1p(h*log_alpha))/(np.log1p(h)-np.log1p(h*log_alpha))*h

    spectrum_new = np.zeros_like(spectrum)
    for i in range(h):
        spectrum_new[int(index[i]):int(index[i+1])] = spectrum[i]
    spectrum = spectrum_new

    return spectrum

