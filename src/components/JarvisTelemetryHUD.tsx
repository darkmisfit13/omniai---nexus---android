import React from 'react';
import { 
  ShieldCheck, 
  User, 
  Database, 
  Flame, 
  ChevronDown
} from 'lucide-react';
import { HoloOrb } from './HoloOrb';

export interface TelemetryNoteItem {
  id: string;
  category: 'Entity' | 'Topic' | 'Emotion' | 'Commitment' | 'Risk' | 'Auto-Pilot';
  label: string;
  detail: string;
  timestamp: string;
}

interface JarvisTelemetryHUDProps {
  isOpen: boolean;
  onClose: () => void;
  activeScreenTitle: string;
  detectedSpeaker: string;
  emotionalRegister: string;
  telemetryNotes: TelemetryNoteItem[];
  latencyMs: number;
  npuAccelerator: string;
  onClearNotes?: () => void;
}

export const JarvisTelemetryHUD: React.FC<JarvisTelemetryHUDProps> = ({
  isOpen,
  onClose,
  activeScreenTitle,
  detectedSpeaker,
  emotionalRegister,
  telemetryNotes,
  latencyMs,
  npuAccelerator,
  onClearNotes,
}) => {
  if (!isOpen) return null;

  return (
    <div className="absolute inset-x-2 bottom-16 z-50 animate-in slide-in-from-bottom-6 duration-200">
      <div className="rounded-2xl border border-[#1e1642] shadow-[0_0_24px_rgba(12,0,202,0.45)] bg-[#0a071b] p-4 text-[#f1f5f9] relative overflow-hidden">
        {/* Top Glowing Ambient Bar */}
        <div className="absolute inset-x-0 top-0 h-1 bg-gradient-to-r from-[#0C00CA] via-[#1a0fe0] to-[#00FFFF]" />

        {/* HUD Header */}
        <div className="flex items-center justify-between pb-3 border-b border-[#1e1642]">
          <div className="flex items-center gap-2.5">
            <HoloOrb phase="analyzing" size="sm" />
            <div>
              <div className="flex items-center gap-2">
                <span className="text-xs font-mono font-bold tracking-wider text-[#00FFFF]">
                  OMNICORE TELEMETRY HUD
                </span>
                <span className="px-1.5 py-0.2 rounded text-[9px] font-mono bg-[#00FFFF]/15 text-[#00FFFF] border border-[#00FFFF]/40">
                  OMNICORE LIVE
                </span>
              </div>
              <span className="text-[10px] text-[#948eb5] font-mono">
                {activeScreenTitle} • <span className="text-[#00FFFF] font-bold">{latencyMs}ms</span> ({npuAccelerator})
              </span>
            </div>
          </div>

          <button
            onClick={onClose}
            className="p-1.5 rounded-lg bg-[#000000] border border-[#1e1642] text-[#948eb5] hover:text-[#00FFFF] hover:border-[#0C00CA] transition"
          >
            <ChevronDown className="w-4 h-4" />
          </button>
        </div>

        {/* Speaker & Emotional Register Banner */}
        <div className="grid grid-cols-2 gap-2 my-3">
          <div className="p-2.5 rounded-xl bg-[#000000] border border-[#1e1642]">
            <div className="flex items-center gap-1.5 text-[10px] font-mono text-[#00FFFF] mb-0.5">
              <User className="w-3 h-3 text-[#00FFFF]" />
              <span>COUNTERPART DOSSIER</span>
            </div>
            <p className="text-xs font-semibold text-[#f1f5f9] truncate">
              {detectedSpeaker || 'Active Participant'}
            </p>
          </div>

          <div className="p-2.5 rounded-xl bg-[#000000] border border-[#1e1642]">
            <div className="flex items-center gap-1.5 text-[10px] font-mono text-[#00FFFF] mb-0.5">
              <Flame className="w-3 h-3 text-[#00FFFF]" />
              <span>EMOTIONAL SUBTEXT</span>
            </div>
            <p className="text-xs font-semibold text-[#f1f5f9] truncate">
              {emotionalRegister || 'Raw & Unflinching'}
            </p>
          </div>
        </div>

        {/* Background Extracted Notes & Facts Feed */}
        <div className="space-y-2">
          <div className="flex items-center justify-between text-[10px] font-mono text-[#948eb5]">
            <span className="flex items-center gap-1 text-[#00FFFF]">
              <Database className="w-3 h-3 text-[#00FFFF]" />
              <span>AUTOMATIC MEMORY LEDGER ({telemetryNotes.length})</span>
            </span>
            {onClearNotes && (
              <button onClick={onClearNotes} className="text-[#948eb5] hover:text-[#00FFFF] transition">
                Clear
              </button>
            )}
          </div>

          <div className="max-h-48 overflow-y-auto no-scrollbar space-y-1.5">
            {telemetryNotes.map((note) => {
              const badgeColors: Record<string, string> = {
                Entity: 'bg-[#000000] text-[#00FFFF] border-[#00FFFF]/40',
                Topic: 'bg-[#000000] text-[#00FFFF] border-[#00FFFF]/40',
                Emotion: 'bg-[#0a071b] text-[#00FFFF] border-[#0C00CA]/60',
                Commitment: 'bg-[#0a071b] text-[#00FFFF] border-[#00FFFF]/60',
                Risk: 'bg-[#0a071b] text-[#00FFFF] border-[#0C00CA]/80',
                'Auto-Pilot': 'bg-[#000000] text-[#00FFFF] border-[#0C00CA] shadow-[0_0_8px_rgba(12,0,202,0.4)]',
              };

              return (
                <div
                  key={note.id}
                  className="flex items-start justify-between gap-2 p-2 rounded-xl bg-[#000000] border border-[#1e1642] text-xs hover:border-[#0C00CA] transition"
                >
                  <div className="flex items-start gap-2 flex-1">
                    <span
                      className={`px-1.5 py-0.5 rounded text-[9px] font-mono font-bold uppercase shrink-0 border ${
                        badgeColors[note.category] || 'bg-[#000000] text-[#00FFFF] border-[#00FFFF]/40'
                      }`}
                    >
                      {note.category}
                    </span>
                    <div>
                      <span className="font-semibold text-[#f1f5f9] block text-[11px]">
                        {note.label}
                      </span>
                      <p className="text-[10px] text-[#948eb5] leading-tight">
                        {note.detail}
                      </p>
                    </div>
                  </div>
                  <span className="text-[9px] font-mono text-[#948eb5] shrink-0">
                    {note.timestamp}
                  </span>
                </div>
              );
            })}
          </div>
        </div>

        {/* Footer */}
        <div className="mt-3 pt-2 border-t border-[#1e1642] flex items-center justify-between text-[10px] font-mono text-[#948eb5]">
          <span className="flex items-center gap-1 text-[#00FFFF]">
            <ShieldCheck className="w-3 h-3 text-[#00FFFF]" /> Shielded Dual-Lane Processing
          </span>
          <span className="text-[#948eb5]">DarkMisfitStudios Register</span>
        </div>
      </div>
    </div>
  );
};

export const OmniCoreTelemetryHUD = JarvisTelemetryHUD;
