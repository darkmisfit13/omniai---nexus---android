import React, { useState } from 'react';
import { 
  Code, 
  Terminal, 
  Sparkles, 
  Zap, 
  Play, 
  Copy, 
  Check, 
  Layers, 
  Monitor, 
  Tablet, 
  Smartphone, 
  RotateCcw 
} from 'lucide-react';
import { HoloOrb } from '../HoloOrb';

export const OmniCodeStudio: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'prompt' | 'code' | 'preview'>('preview');
  const [activeViewport, setActiveViewport] = useState<'desktop' | 'tablet' | 'mobile'>('desktop');
  const [appIdeaPrompt, setAppIdeaPrompt] = useState('Build a nocturnal beat-sync music visualizer with volumetric spectrum analyzer and dark obsidian cards.');
  const [isBuilding, setIsBuilding] = useState(false);
  const [buildProgress, setBuildProgress] = useState(100);
  const [isBuilt, setIsBuilt] = useState(true);
  const [isCopied, setIsCopied] = useState(false);

  const sampleGeneratedCode = `import React, { useState } from 'react';
import { Play, Pause, Sparkles, Activity } from 'lucide-react';

export default function NocturnalBeatApp() {
  const [isPlaying, setIsPlaying] = useState(false);

  return (
    <div className="min-h-screen bg-[#000000] text-[#f1f5f9] p-6 font-sans">
      <header className="flex justify-between items-center border-b border-[#1e1642] pb-4 mb-6">
        <h1 className="text-xl font-bold tracking-wider text-[#00FFFF] flex items-center gap-2">
          <Sparkles className="w-5 h-5 text-[#00FFFF]" /> NOCTURNAL BEAT ENGINE
        </h1>
        <span className="px-2.5 py-1 rounded-full text-xs font-mono bg-[#0a071b] border border-[#0C00CA] text-[#00FFFF]">
          140 BPM PHONK
        </span>
      </header>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="p-5 rounded-2xl bg-[#0a071b] border border-[#1e1642] shadow-[0_0_20px_rgba(12,0,202,0.35)]">
          <h2 className="text-sm font-bold text-white mb-2">SPECTRUM TELEMETRY</h2>
          <div className="h-28 flex items-end gap-1.5 pt-4">
            {[45, 80, 60, 95, 70, 40, 85, 90, 65, 30, 80, 100].map((h, i) => (
              <div 
                key={i} 
                className="flex-1 bg-gradient-to-t from-[#0C00CA] to-[#00FFFF] rounded-t-sm"
                style={{ height: \`\${h}%\` }}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}`;

  const handleStartBuild = () => {
    setIsBuilding(true);
    setBuildProgress(0);
    const interval = setInterval(() => {
      setBuildProgress((prev) => {
        if (prev >= 100) {
          clearInterval(interval);
          setIsBuilding(false);
          setIsBuilt(true);
          return 100;
        }
        return prev + 20;
      });
    }, 250);
  };

  const handleCopyCode = () => {
    navigator.clipboard.writeText(sampleGeneratedCode);
    setIsCopied(true);
    setTimeout(() => setIsCopied(false), 2000);
  };

  return (
    <div className="h-full flex flex-col bg-[#000000] text-[#f1f5f9] overflow-hidden select-none">
      {/* Studio Top Control Strip */}
      <div className="h-14 border-b border-[#1e1642] px-4 flex items-center justify-between bg-[#000000] shrink-0">
        <div className="flex items-center gap-3">
          <div className="p-2 rounded-xl bg-[#0a071b] border border-[#0C00CA] shadow-[0_0_12px_rgba(12,0,202,0.4)]">
            <Code className="w-5 h-5 text-[#00FFFF]" />
          </div>
          <div>
            <div className="flex items-center gap-2">
              <span className="text-sm font-bold text-white">OmniCode Studio</span>
              <span className="px-1.5 py-0.5 rounded text-[9px] font-mono bg-[#0a071b] text-[#00FFFF] border border-[#00FFFF]/40">
                PROTOTYPE AGENT
              </span>
            </div>
            <p className="text-[11px] text-[#948eb5]">Generate and render interactive components from natural language</p>
          </div>
        </div>

        {isBuilt && (
          <div className="flex items-center gap-2">
            <div className="flex items-center bg-[#0a071b] rounded-lg p-0.5 border border-[#1e1642]">
              <button
                onClick={() => setActiveViewport('desktop')}
                className={`p-1.5 rounded ${activeViewport === 'desktop' ? 'bg-[#0C00CA] text-white shadow-[0_0_10px_rgba(12,0,202,0.6)]' : 'text-[#948eb5]'}`}
                title="Desktop"
              >
                <Monitor className="w-3.5 h-3.5" />
              </button>
              <button
                onClick={() => setActiveViewport('tablet')}
                className={`p-1.5 rounded ${activeViewport === 'tablet' ? 'bg-[#0C00CA] text-white shadow-[0_0_10px_rgba(12,0,202,0.6)]' : 'text-[#948eb5]'}`}
                title="Tablet"
              >
                <Tablet className="w-3.5 h-3.5" />
              </button>
              <button
                onClick={() => setActiveViewport('mobile')}
                className={`p-1.5 rounded ${activeViewport === 'mobile' ? 'bg-[#0C00CA] text-white shadow-[0_0_10px_rgba(12,0,202,0.6)]' : 'text-[#948eb5]'}`}
                title="Mobile"
              >
                <Smartphone className="w-3.5 h-3.5" />
              </button>
            </div>

            <button
              onClick={() => setIsBuilt(false)}
              className="px-3 py-1.5 rounded-lg bg-[#0a071b] border border-[#1e1642] hover:border-[#0C00CA] text-xs text-[#f1f5f9] flex items-center gap-1.5"
            >
              <RotateCcw className="w-3.5 h-3.5" />
              New Build
            </button>
          </div>
        )}
      </div>

      {/* Main Studio Body */}
      <div className="flex-1 overflow-hidden flex flex-col p-4 space-y-4">
        {/* Prompt Input Section */}
        <div className="p-4 rounded-xl bg-[#0a071b] border border-[#1e1642] space-y-3">
          <div className="flex items-center justify-between">
            <span className="text-xs font-mono text-[#00FFFF] uppercase tracking-wider flex items-center gap-1.5 font-bold">
              <Sparkles className="w-3.5 h-3.5 text-[#00FFFF]" /> Natural Language App Specification
            </span>
            <span className="text-[10px] font-mono text-[#948eb5]">Kotlin Compose • React 19 • Tailwind CSS</span>
          </div>

          <div className="flex flex-col sm:flex-row gap-2">
            <textarea
              value={appIdeaPrompt}
              onChange={(e) => setAppIdeaPrompt(e.target.value)}
              placeholder="Describe the application you want to build..."
              rows={2}
              className="flex-1 bg-[#000000] border border-[#1e1642] rounded-lg p-2.5 text-xs text-[#f1f5f9] placeholder-[#948eb5] focus:outline-none focus:border-[#0C00CA] focus:shadow-[0_0_14px_rgba(12,0,202,0.45)] font-mono resize-none"
            />
            <button
              onClick={handleStartBuild}
              disabled={isBuilding}
              className="px-5 py-2.5 rounded-lg bg-[#0C00CA] hover:bg-[#1a0fe0] text-white text-xs font-bold flex items-center justify-center gap-2 shadow-[0_0_16px_rgba(12,0,202,0.55)] transition-all shrink-0 active:scale-95 disabled:opacity-50"
            >
              <Zap className="w-4 h-4 fill-current text-[#00FFFF]" />
              {isBuilding ? 'Forging App...' : 'Generate App'}
            </button>
          </div>
        </div>

        {/* Build Progress Indicator */}
        {isBuilding && (
          <div className="p-4 rounded-xl bg-[#0a071b] border border-[#0C00CA]/50 space-y-2 animate-in fade-in">
            <div className="flex items-center justify-between text-xs font-mono">
              <span className="text-[#00FFFF] flex items-center gap-2">
                <HoloOrb phase="processing" size="sm" />
                Compiling components, layouts and API endpoints...
              </span>
              <span className="text-[#00FFFF] font-bold">{buildProgress}%</span>
            </div>
            <div className="w-full bg-[#000000] h-2 rounded-full overflow-hidden border border-[#1e1642]">
              <div 
                className="bg-gradient-to-r from-[#0C00CA] to-[#00FFFF] h-full transition-all duration-300"
                style={{ width: `${buildProgress}%` }}
              />
            </div>
          </div>
        )}

        {/* App Preview & Canvas */}
        <div className="flex-1 min-h-[300px] rounded-xl border border-[#1e1642] bg-[#000000] overflow-hidden flex flex-col relative">
          <div className="h-8 bg-[#0a071b] border-b border-[#1e1642] px-3 flex items-center justify-between text-[11px] font-mono text-[#948eb5]">
            <div className="flex items-center gap-1.5">
              <span className="w-2 h-2 rounded-full bg-[#0C00CA]" />
              <span className="w-2 h-2 rounded-full bg-[#00FFFF]" />
              <span className="ml-2 text-[#f1f5f9]">preview://omni.nexus/engine</span>
            </div>
            <div className="flex items-center gap-2">
              <span className="text-[10px] text-[#00FFFF] bg-[#00FFFF]/15 px-1.5 py-0.5 rounded border border-[#00FFFF]/40">
                Ready • 100% Locked
              </span>
            </div>
          </div>

          <div className="flex-1 flex items-center justify-center p-4 overflow-y-auto">
            {isBuilt ? (
              <div className={`transition-all duration-300 shadow-[0_0_24px_rgba(12,0,202,0.35)] rounded-xl border border-[#1e1642] bg-[#0a071b] p-6 ${
                activeViewport === 'mobile' ? 'w-[320px] h-[540px]' : activeViewport === 'tablet' ? 'w-[520px] h-[480px]' : 'w-full h-full max-w-4xl'
              }`}>
                <div className="h-full flex flex-col justify-between">
                  <div className="space-y-4">
                    <div className="flex items-center justify-between border-b border-[#1e1642] pb-3">
                      <span className="font-mono text-sm font-bold text-white tracking-wider flex items-center gap-1.5">
                        <Sparkles className="w-4 h-4 text-[#00FFFF]" /> DARK MISFIT STUDIOS
                      </span>
                      <span className="px-2 py-0.5 rounded text-[10px] font-mono bg-[#0C00CA]/30 text-[#00FFFF] border border-[#0C00CA]">
                        ABSOLUTE BLACK CYBERPUNK
                      </span>
                    </div>
                    <h1 className="text-xl font-black text-transparent bg-clip-text bg-gradient-to-r from-[#00FFFF] via-white to-[#0C00CA]">
                      Raw Emotion Transformed Into Visual Art
                    </h1>
                    <p className="text-xs text-[#948eb5] leading-relaxed">
                      Autonomous intelligence, shadow aesthetics, and high-velocity mobile cognition engineered by Benjamin James.
                    </p>
                    <div className="grid grid-cols-2 gap-3 pt-2">
                      <div className="p-3 rounded-lg bg-[#000000] border border-[#1e1642]">
                        <div className="text-[10px] text-[#948eb5] font-mono">DIGITAL SHADOW</div>
                        <div className="text-sm font-bold text-white mt-1">OmniAI Engine</div>
                      </div>
                      <div className="p-3 rounded-lg bg-[#000000] border border-[#1e1642]">
                        <div className="text-[10px] text-[#948eb5] font-mono">TELEMETRY LATENCY</div>
                        <div className="text-sm font-bold text-[#00FFFF] mt-1">4.2ms Speculative</div>
                      </div>
                    </div>
                  </div>

                  <div className="pt-4 border-t border-[#1e1642] flex items-center justify-between">
                    <span className="text-[10px] text-[#948eb5] font-mono">Built with OmniCode</span>
                    <button className="px-3 py-1.5 rounded-lg bg-[#0C00CA] hover:bg-[#1a0fe0] text-white text-xs font-bold transition shadow-[0_0_12px_rgba(12,0,202,0.5)]">
                      Live Preview Active
                    </button>
                  </div>
                </div>
              </div>
            ) : (
              <div className="text-center space-y-2">
                <Terminal className="w-10 h-10 text-[#00FFFF] mx-auto animate-pulse" />
                <p className="text-xs font-mono text-[#948eb5]">Enter specification above and click Generate App</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
