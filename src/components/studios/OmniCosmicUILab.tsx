import React, { useState } from 'react';
import { 
  Palette, 
  Sparkles, 
  Copy, 
  Check, 
  Layers, 
  Monitor, 
  Tablet, 
  Smartphone, 
  Glasses, 
  Sliders, 
  Upload, 
  Maximize2 
} from 'lucide-react';
import { HoloOrb } from '../HoloOrb';

export const OmniCosmicUILab: React.FC = () => {
  const [viewport, setViewport] = useState<'desktop' | 'tablet' | 'mobile' | 'spatial'>('desktop');
  const [aestheticMood, setAestheticMood] = useState<'nocturnal_cyber' | 'glassmorphism' | 'neo_brutalist' | 'minimal_dark'>('nocturnal_cyber');
  const [primaryColor, setPrimaryColor] = useState('#0C00CA');
  const [secondaryColor, setSecondaryColor] = useState('#00FFFF');
  const [extractedStyle, setExtractedStyle] = useState<string | null>(null);
  const [isCopied, setIsCopied] = useState(false);
  const [orbPhase, setOrbPhase] = useState<any>('suggesting');

  const handleImageExtract = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setOrbPhase('analyzing');
      setTimeout(() => {
        setExtractedStyle('Extracted: Absolute Black Cyberpunk • Obsidian-Purple #0a071b • Purplish-Blue #0C00CA • Baby Blue #00FFFF');
        setPrimaryColor('#0C00CA');
        setSecondaryColor('#00FFFF');
        setOrbPhase('directAction');
      }, 1000);
    }
  };

  const previewComponentCode = `<div className="relative group p-6 rounded-2xl bg-[#0a071b] border border-[#1e1642] hover:border-[#0C00CA] transition-all duration-300 shadow-[0_0_24px_rgba(12,0,202,0.45)]">
  <div className="flex items-center justify-between mb-4">
    <span className="text-xs font-mono font-bold text-[#00FFFF] uppercase">
      ABSOLUTE BLACK CYBERPUNK
    </span>
    <span className="px-2 py-0.5 rounded text-[10px] font-mono bg-[#0C00CA] text-white">
      PRO PRODUCTION
    </span>
  </div>
  <h3 className="text-lg font-bold text-white mb-2">Neural Telemetry Gateway</h3>
  <p className="text-xs text-[#948eb5] mb-4 leading-relaxed">
    Zero-latency edge routing with holographic orb state syncing across viewports.
  </p>
  <button className="w-full py-2.5 rounded-xl bg-[#0C00CA] hover:bg-[#1a0fe0] text-white font-bold text-xs shadow-[0_0_14px_rgba(12,0,202,0.5)]">
    Authorize Synapse Pipe
  </button>
</div>`;

  const handleCopyCode = () => {
    navigator.clipboard.writeText(previewComponentCode);
    setIsCopied(true);
    setTimeout(() => setIsCopied(false), 2000);
  };

  return (
    <div className="h-full flex flex-col bg-[#000000] text-[#f1f5f9] overflow-hidden select-none font-sans">
      {/* Top Header */}
      <div className="h-14 border-b border-[#1e1642] px-4 flex items-center justify-between bg-[#000000] shrink-0">
        <div className="flex items-center gap-3">
          <div className="p-2 rounded-xl bg-[#0a071b] border border-[#0C00CA] shadow-[0_0_12px_rgba(12,0,202,0.4)]">
            <Palette className="w-5 h-5 text-[#00FFFF]" />
          </div>
          <div>
            <h3 className="text-sm font-bold text-white flex items-center gap-2">
              OMNIAI - NEXUS COSMIC UI LAB
              <span className="text-[10px] font-mono px-2 py-0.5 rounded-full bg-[#0a071b] text-[#00FFFF] border border-[#00FFFF]/40">
                GENERATIVE COMPONENT CANVAS
              </span>
            </h3>
            <p className="text-[11px] text-[#948eb5]">Generate production Kotlin Compose & React components across Desktop, Mobile & Spatial AR</p>
          </div>
        </div>

        {/* Viewport switchers */}
        <div className="flex items-center bg-[#0a071b] rounded-lg p-0.5 border border-[#1e1642]">
          {[
            { id: 'desktop', label: 'Desktop', icon: Monitor },
            { id: 'tablet', label: 'Tablet', icon: Tablet },
            { id: 'mobile', label: 'Mobile', icon: Smartphone },
            { id: 'spatial', label: 'Spatial AR', icon: Glasses },
          ].map((vp) => {
            const Icon = vp.icon;
            return (
              <button
                key={vp.id}
                onClick={() => setViewport(vp.id as any)}
                className={`flex items-center gap-1.5 px-3 py-1 rounded text-xs font-mono font-medium transition-all ${
                  viewport === vp.id ? 'bg-[#0C00CA] text-white font-bold shadow-[0_0_10px_rgba(12,0,202,0.6)]' : 'text-[#948eb5] hover:text-white'
                }`}
              >
                <Icon className="w-3.5 h-3.5" />
                {vp.label}
              </button>
            );
          })}
        </div>
      </div>

      {/* Main Studio Body */}
      <div className="flex-1 flex flex-col md:flex-row overflow-hidden">
        {/* Component Live Canvas */}
        <div className="flex-1 bg-[#000000] flex flex-col items-center justify-center p-6 relative overflow-hidden">
          <div
            className={`transition-all duration-300 flex items-center justify-center ${
              viewport === 'desktop' ? 'w-full max-w-xl' : viewport === 'tablet' ? 'w-[420px]' : viewport === 'mobile' ? 'w-[320px]' : 'w-[480px] p-8 rounded-3xl border border-[#1e1642] bg-[#0a071b]/80'
            }`}
          >
            {/* Live Generative Pricing Card */}
            <div className="w-full relative group p-6 rounded-2xl bg-[#0a071b] border border-[#1e1642] hover:border-[#0C00CA] transition-all duration-300 shadow-[0_0_24px_rgba(12,0,202,0.45)]">
              <div
                style={{
                  background: `linear-gradient(135deg, ${primaryColor}, ${secondaryColor})`,
                }}
                className="absolute -inset-0.5 rounded-2xl opacity-20 group-hover:opacity-60 transition duration-500 blur-sm -z-10"
              />

              <div className="flex items-center justify-between mb-2">
                <span className="text-[10px] font-mono font-bold text-[#00FFFF] uppercase tracking-widest px-2 py-0.5 rounded bg-[#000000] border border-[#00FFFF]/40">
                  COSMIC DESIGN TOKEN
                </span>
                <span className="text-[10px] font-mono text-[#00FFFF] font-bold">140 BPM Master</span>
              </div>

              <h4 className="text-lg font-black text-white mt-2">Nocturnal Obsidian Pass</h4>
              <p className="text-xs text-[#948eb5] mt-1 leading-relaxed">
                Autonomous agent council, real-time screen vision OCR, and unlimited multi-track studio rendering.
              </p>

              <div className="my-5 p-3 rounded-xl bg-[#000000] border border-[#1e1642] flex items-baseline justify-between">
                <div>
                  <span className="text-2xl font-black text-white">$29</span>
                  <span className="text-xs text-[#948eb5] ml-1">/ lifetime</span>
                </div>
                <span className="text-[10px] font-mono text-[#00FFFF]">Zero Subscription Lock</span>
              </div>

              <button
                className="w-full py-3 rounded-xl font-bold text-xs uppercase tracking-wider text-white shadow-[0_0_16px_rgba(12,0,202,0.55)] transition-all active:scale-95 bg-[#0C00CA] hover:bg-[#1a0fe0]"
              >
                Deploy Component Pipeline
              </button>
            </div>
          </div>
        </div>

        {/* Right Configuration Deck */}
        <div className="w-full md:w-80 bg-[#0a071b] border-l border-[#1e1642] p-4 flex flex-col justify-between space-y-4 overflow-y-auto no-scrollbar">
          <div className="space-y-4">
            {/* Omni Orb Assistant */}
            <div className="p-3.5 rounded-xl bg-[#000000] border border-[#1e1642] flex items-center gap-3">
              <div className="w-12 h-12 flex-shrink-0">
                <HoloOrb phase={orbPhase} className="w-full h-full" />
              </div>
              <div className="min-w-0">
                <span className="text-[10px] font-mono text-[#00FFFF] font-bold uppercase block">COSMIC UI TOKEN ENGINE</span>
                <p className="text-xs text-[#948eb5]">"Tune primary colors, aesthetic mood tokens, or upload image to extract style."</p>
              </div>
            </div>

            {/* Extract Style From Image */}
            <div className="p-3 rounded-xl bg-[#000000] border border-[#1e1642] space-y-2">
              <span className="text-[10px] font-mono font-bold text-[#00FFFF] uppercase flex items-center gap-1.5">
                <Upload className="w-3.5 h-3.5 text-[#00FFFF]" /> Extract Style From Image
              </span>
              <label className="w-full p-2 rounded-lg bg-[#0a071b] border border-dashed border-[#1e1642] hover:border-[#0C00CA] text-center block text-xs text-[#948eb5] cursor-pointer">
                Upload UI Screenshot
                <input type="file" accept="image/*" onChange={handleImageExtract} className="hidden" />
              </label>
              {extractedStyle && (
                <p className="text-[10px] font-mono text-[#00FFFF]">{extractedStyle}</p>
              )}
            </div>

            {/* Aesthetic Mood Selector */}
            <div className="space-y-1">
              <label className="text-[10px] font-mono text-[#948eb5] uppercase font-bold">Aesthetic Mood</label>
              <select
                value={aestheticMood}
                onChange={(e) => setAestheticMood(e.target.value as any)}
                className="w-full bg-[#000000] border border-[#1e1642] focus:border-[#0C00CA] rounded-lg p-2 text-xs text-[#00FFFF] font-mono outline-none"
              >
                <option value="nocturnal_cyber">Nocturnal Cyber (OmniAI - Nexus)</option>
                <option value="glassmorphism">Glassmorphism Frosted Void</option>
                <option value="neo_brutalist">Neo-Brutalist Stark Contrast</option>
                <option value="minimal_dark">Minimalist Stealth Black</option>
              </select>
            </div>

            {/* Color Pickers */}
            <div className="grid grid-cols-2 gap-3 pt-2 border-t border-[#1e1642]">
              <div className="space-y-1">
                <span className="text-[10px] font-mono text-[#948eb5] block">Primary Accent</span>
                <div className="flex items-center gap-2 bg-[#000000] p-1.5 rounded-lg border border-[#1e1642]">
                  <input
                    type="color"
                    value={primaryColor}
                    onChange={(e) => setPrimaryColor(e.target.value)}
                    className="w-6 h-6 rounded bg-transparent cursor-pointer border-0"
                  />
                  <span className="text-[10px] font-mono text-white">{primaryColor}</span>
                </div>
              </div>
              <div className="space-y-1">
                <span className="text-[10px] font-mono text-[#948eb5] block">Diagnostic Metric</span>
                <div className="flex items-center gap-2 bg-[#000000] p-1.5 rounded-lg border border-[#1e1642]">
                  <input
                    type="color"
                    value={secondaryColor}
                    onChange={(e) => setSecondaryColor(e.target.value)}
                    className="w-6 h-6 rounded bg-transparent cursor-pointer border-0"
                  />
                  <span className="text-[10px] font-mono text-white">{secondaryColor}</span>
                </div>
              </div>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="pt-2 border-t border-[#1e1642] space-y-2">
            <button
              onClick={handleCopyCode}
              className="w-full py-2.5 rounded-xl bg-[#000000] border border-[#1e1642] hover:border-[#0C00CA] text-xs font-mono text-[#f1f5f9] flex items-center justify-center gap-2 transition"
            >
              {isCopied ? <Check className="w-3.5 h-3.5 text-[#00FFFF]" /> : <Copy className="w-3.5 h-3.5" />}
              {isCopied ? 'Copied Component' : 'Copy Clean Code'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
